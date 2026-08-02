/*
 * This file is part of BreakTheLibrary.
 *
 * BreakTheLibrary is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BreakTheLibrary is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BreakTheLibrary. If not, see <https://www.gnu.org/licenses/>.
 */
package org.breakthebot.breakthelibrary.api

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.future.await
import kotlinx.coroutines.future.future
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.serializer
import org.breakthebot.breakthelibrary.BreakTheLibrary
import org.breakthebot.breakthelibrary.models.APIResult
import org.breakthebot.breakthelibrary.models.Template
import org.breakthebot.breakthelibrary.utils.Config
import org.breakthebot.breakthelibrary.utils.ConfigHandler
import org.jetbrains.annotations.ApiStatus
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.net.http.HttpTimeoutException
import java.time.Duration
import java.util.concurrent.CompletableFuture

/**
 * Wrapper for interacting with the web APIs in a clean way.
 * @param json The JSON parser to use.
 * @property client The http client the ApiClient uses.
 * */
open class BaseAPIClient(
    val json: Json,
) {
    val client: HttpClient = HttpClient.newHttpClient()

    /** Sends a get request.
     * @param url The url to send the request to.
     * @param T The type of the data that should be returned on success.
     * @param serializer The serializer of the object.
     * @throws HttpTimeoutException If the request surpasses the timeout defined in [Config.requestTimeOut].
     * @return Return [APIResult] with T as a type param.
     * */
    suspend fun <T> getRequest(url: String, serializer: KSerializer<T>): APIResult<T> {
        val request = HttpRequest
            .newBuilder()
            .apply {
                uri(URI(url))
                header("Content-Type", "application/json")
                timeout(timeout)
            }
            .build()
        try {
            val response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).await()
            if (response.statusCode() != 200) {
                return APIResult.Error(
                    response.body(),
                    response.statusCode(),
                )
            }
            return APIResult.Success(
                parseString(response.body(), serializer),
                200,
            )
        } catch (_: HttpTimeoutException) {
            return APIResult.Error(
                "Request timed out.",
                408,
            )
        } catch (e: Exception) {
            return APIResult.Error(
                e.message ?: "Unknown error",
                0,
            )
        }
    }

    /** Send a post request to the specified url, cannot handle more than [Config.batchSize].
     * @param url The url to send the request to.
     * @param body The request body.
     * @param serializer The serializer of the object.
     * @param T The type of the data that should be returned on success.
     * @throws HttpTimeoutException If the request surpasses the timeout defined in [Config.requestTimeOut].
     * @return Return [APIResult] with T as a type param.
     * */
    suspend fun <T> postRequest(
        url: String,
        body: String,
        serializer: KSerializer<T>,
    ): APIResult<T> {
        val request = HttpRequest
            .newBuilder()
            .apply {
                uri(URI(url))
                header("Content-Type", "application/json")
                POST(HttpRequest.BodyPublishers.ofString(body))
                timeout(timeout)
            }
            .build()
        try {
            val response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).await()
            val body = response.body()
            if (response.statusCode() != 200) {
                return APIResult.Error(
                    // In the context of towny APIs this will almost always be the error message.
                    body,
                    response.statusCode(),
                )
            }
            // If body is [], then query is 404 but api does not return it.
            if (body == "[]") {
                return APIResult.Error(
                    "Not found",
                    404,
                )
            }
            return APIResult.Success(
                parseString(body, serializer),
                200,
            )
        } catch (_: HttpTimeoutException) {
            return APIResult.Error(
                "Request timed out.",
                408,
            )
        } catch (e: Exception) {
            return APIResult.Error(
                e.message ?: "Unknown error",
                0,
            )
        }
    }

    /** Send a request with a JSON payload without parsing to str.
     * @param url The url to send the request to.
     * @param body The JSON body.
     * @param T The type of the data that should be returned on success.
     * @throws HttpTimeoutException If the request surpasses the timeout defined in [Config.requestTimeOut].
     * @return Return [APIResult] with T as a type param.
     * */
    suspend inline fun <reified T> postRequest(
        url: String,
        body: JsonObject,
    ): APIResult<T> = postRequest(url, body.toString(), serializer<T>())

    /**
     * Sends a POST request to the specified url.
     * @param url The url to send the request to.
     * @param body The items to query.
     * @param template A template for the items.
     * @param serializer The list serializer of the return type.
     * @param R The type of the response.
     * @param T The template type.
     * @throws HttpTimeoutException If the request surpasses the timeout defined in [Config.requestTimeOut].
     * @return Return [APIResult] with List<T> as a type param.
     * */
    suspend fun <R, T : Template> postRequest(
        url: String,
        body: Collection<String>,
        template: T,
        templateSerializer: KSerializer<T>,
        serializer: KSerializer<List<R>>,
    ): APIResult<List<R>> {
        val body = buildJsonObject {
            put("query", Json.encodeToJsonElement(body))
            put("template", Json.encodeToJsonElement(templateSerializer, template))
        }.toString()
        return postRequest(url, body, serializer)
    }

    /**
     * Sends a post request to the specified url.
     * @param url The url to send the request to.
     * @param body The items to query.
     * @param template A template for the items that implements [Template].
     * @param R The type of the response.
     * @param T The template type.
     * @throws HttpTimeoutException If the request surpasses the timeout defined in [Config.requestTimeOut].
     * @return Return [APIResult] with List<T> as a type param.
     * */
    suspend inline fun <reified R, reified T : Template> postRequest(
        url: String,
        body: Collection<String>,
        template: T,
    ): APIResult<List<R>> = postRequest(url, body, template, serializer<T>(), serializer<List<R>>())

    /** Send a request with a JSON payload without parsing to str.
     * @param url The url to send the request to.
     * @param body The items to query.
     * @param serializer The serializer of the item.
     * @param T The type of the data that should be returned on success.
     * @throws HttpTimeoutException If the request surpasses the timeout defined in [Config.requestTimeOut].
     * @return Return [APIResult] with T as a type param.
     * */
    suspend fun <T> postRequest(url: String, body: Collection<String>, serializer: KSerializer<List<T>>): APIResult<List<T>> {
        if (body.size > ConfigHandler.cfg.batchSize) {
            return APIResult.Error(
                "Unable to send request, query size is bigger than ${ConfigHandler.cfg.batchSize}",
                400,
            )
        }
        val uuids = body
            .toString()
            .removePrefix("[")
            .removeSuffix("]")
            .split(",")
            .map { it.trim().removeSurrounding("\"") }

        val jsonBody = buildJsonObject {
            put("query", JsonArray(uuids.map { JsonPrimitive(it) }))
        }
        return postRequest(url, jsonBody.toString(), serializer)
    }

    /** Post request a singular item without having to construct an object
     * by adding everything from body to a JSON object with a key named `query` that is a JSON array.
     * @param T The type of the objects you want to fetch.
     * @param url The url to send the req to
     * @param name The name or stringified UUID to query.
     * @throws HttpTimeoutException If the request surpasses the timeout defined in [Config.requestTimeOut].
     * @return Return [APIResult] with List<T> as a type param.
     * */
    suspend inline fun <reified T> postRequestItem(
        url: String,
        name: String,
    ): APIResult<T> {
        val jsonBody = buildJsonObject {
            put("query", JsonArray(listOf(JsonPrimitive(name))))
        }
        return postRequest(url, jsonBody.toString(), serializer<T>())
    }

    /** Fetch items that are over [Config.batchSize].
     * @param names The names or UUIDs of the items to fetch.
     * @param url The url.
     * @param serializer The serializer of the object.
     * @param concurrencyLimit The limit of concurrent requests.
     * @throws HttpTimeoutException If the request surpasses the timeout defined in [Config.requestTimeOut].
     * @param T The type that the items should be parsed into.
     * */
    suspend fun <T> getChunked(
        names: Collection<String>,
        url: String,
        serializer: KSerializer<List<T>>,
        concurrencyLimit: Int = 3,
    ): List<APIResult<List<T>>> {
        val batches = names.chunked(ConfigHandler.cfg.batchSize)
        val semaphore = Semaphore(concurrencyLimit)

        val items = coroutineScope {
            batches
                .map { batch ->
                    async {
                        semaphore.withPermit {
                            postRequest(url, batch, serializer)
                        }
                    }
                }.awaitAll()
        }
        return items
    }

    /**
     * [BaseAPIClient.getChunked] implementation with POST templates.
     *
     * @param url The url to send the requests to.
     * @param items The list of items to query.
     * @param template The template which must implement [Template].
     * @param concurrencyLimit How many requests can execute concurrently.
     * @param R The return type for the requests.
     * */
    suspend inline fun <reified R, reified T : Template> getChunked(
        url: String,
        items: Collection<String>,
        template: T,
        concurrencyLimit: Int = 3,
    ): List<APIResult<List<R>>> {
        val batches = items.chunked(ConfigHandler.cfg.batchSize)
        val semaphore = Semaphore(concurrencyLimit)

        val items = coroutineScope {
            batches
                .map { batch ->
                    async {
                        semaphore.withPermit {
                            postRequest<R, T>(url, batch, template)
                        }
                    }
                }.awaitAll()
        }
        return items
    }

    /** Parse an API response into a specified [T] type.
     * @param T The type to attempt to parse the string into.
     * @param body The string to parse into the [T] type.
     * @return The body as T.
     * */
    @ApiStatus.Internal
    fun <T> parseString(body: String, serializer: KSerializer<T>): T = when {
        serializer.descriptor.kind is StructureKind.LIST -> {
            json.decodeFromString(serializer, body)
        }

        else -> {
            val cleaned = body.removePrefix("[").removeSuffix("]")
            json.decodeFromString(serializer, cleaned)
        }
    }

    /**
     * @property timeout The request timeout.
     * */
    companion object {
        val timeout: Duration
            get() = Duration.ofSeconds(ConfigHandler.cfg.requestTimeOut.toLong())
    }

    /**
     * Creates a completable future using [Dispatchers.IO] that yields [T] when completed.
     * */
    fun <T> future(block: suspend () -> T): CompletableFuture<T> = CoroutineScope(Dispatchers.IO).future { block() }
}

object APIClient : BaseAPIClient(BreakTheLibrary.json)
