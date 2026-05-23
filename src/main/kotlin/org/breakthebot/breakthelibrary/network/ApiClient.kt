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
package org.breakthebot.breakthelibrary.network

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.future.await
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.serializer
import org.breakthebot.breakthelibrary.BreakTheLibrary
import org.breakthebot.breakthelibrary.models.ApiResult
import org.breakthebot.breakthelibrary.utils.ConfigHandler
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

/**
 * Wrapper for interacting with the EarthMc API in a clean way.
 * @property client The http client the ApiClient uses.
 * */
object ApiClient {
    val json = BreakTheLibrary.json
    val client: HttpClient = HttpClient.newHttpClient()

    /** Parse a string into a specified T type.
     * @param T The type to attempt to parse the string into.
     * @param body The string to parse into the T type.
     * */
    @OptIn(ExperimentalSerializationApi::class)
    inline fun <reified T> parseString(body: String): T {
        val serializer = json.serializersModule.serializer<T>()
        return when {
            serializer.descriptor.kind is StructureKind.LIST -> {
                json.decodeFromString(serializer, body)
            }
            T::class == String::class -> {
                body as T
            }
            else -> {
                val cleaned = body.removePrefix("[").removeSuffix("]")
                json.decodeFromString(serializer, cleaned)
            }
        }
    }

    /** Sends a get request.
     * @param url The url to send the request to.
     * @param T The type of the data that should be returned on success.
     * @return Return [ApiResult] with T as a type param.
     * */
    suspend inline fun <reified T> getRequest(url: String): ApiResult<T> {
        val request = HttpRequest.newBuilder().apply {
            uri(URI(url))
            header("Content-Type", "application/json")
        }.build()
        try {
            val response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).await()
            if (response.statusCode() != 200) {
                return ApiResult.Error(
                    response.body(),
                    response.statusCode()
                )
            }
            return ApiResult.Success(
                parseString<T>(response.body()),
                200
            )
        } catch (e: Exception) {
            return ApiResult.Error(
                e.message ?: "Unknown error",
                0
            )
        }
    }

    /** Send a post request to the specified url, cannot handle more than Config.batchSize.
     * @param url The url to send the request to.
     * @param body The request body.
     * @param T The type of the data that should be returned on success.
     * @return Return [ApiResult] with T as a type param.
     * */
    suspend inline fun <reified T> postRequest(
        url: String,
        body: String
    ): ApiResult<T> {
        val request = HttpRequest.newBuilder().apply {
           uri(URI(url))
           header("Content-Type", "application/json")
           POST(HttpRequest.BodyPublishers.ofString(body))
        }.build()
        try {
            val response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).await()
            val body = response.body()
            if (response.statusCode() != 200) {
                return ApiResult.Error(
                    // In the context of towny APIs this will almost always be the error message.
                    body,
                    response.statusCode()
                )
            }
            // If body is [], then query is 404 but api does not return it.
            if (body == "[]") {
                return ApiResult.Error(
                    "Not found",
                    404
                )
            }
            return ApiResult.Success(
                parseString<T>(body),
                200
            )
        } catch (e: Exception) {
            return ApiResult.Error(
                e.message ?: "Unknown error",
                0
            )
        }
    }

    /** Send a request with a JSON payload without parsing to str.
     * @param url The url to send the request to.
     * @param body The JSON body.
     * @param T The type of the data that should be returned on success.
     * @return Return [ApiResult] with T as a type param.
     * */
    suspend inline fun <reified T> postRequest(
        url: String,
        body: JsonObject
    ): ApiResult<T> = postRequest<T>(url, body.toString())


    /** Post request items without having to construct an object
     * by adding everything from body to a JSON object with a key named `query` that is a JSON array.
     * @param T The type of the objects you want to fetch.
     * @param url The url to send the req to.
     * @param body A list of UUID's or names to fetch from the specified endpoint.
     * @return Return [ApiResult] with List<T> as a type param.
     * */
    suspend inline fun <reified T> postRequest(
        url: String,
        body: List<String>
    ): ApiResult<List<T>> {
        if (body.size > ConfigHandler.cfg.batchSize) {
            return ApiResult.Error(
                "Unable to send request, query size is bigger than ${ConfigHandler.cfg.batchSize}",
                400
            )
        }
       val uuids = body.toString().removePrefix("[").removeSuffix("]").split(",").map { it.trim().removeSurrounding("\"") }
       val jsonBody = buildJsonObject {
           put("query", JsonArray(uuids.map { JsonPrimitive(it) }))
       }
       return postRequest(url, jsonBody.toString())
    }


    /** Post request a singular item without having to construct an object
     * by adding everything from body to a JSON object with a key named `query` that is a JSON array.
     * @param T The type of the objects you want to fetch.
     * @param url The url to send the req to
     * @param name The name or stringified UUID to query.
     * @return Return [ApiResult] with List<T> as a type param.
     * */
    suspend inline fun <reified T> postRequestItem(
        url: String,
        name: String
    ): ApiResult<T> {
        val jsonBody = buildJsonObject {
            put("query", JsonArray(listOf(JsonPrimitive(name))))
        }
        return postRequest(url, jsonBody.toString())
    }

    /** Fetch items that are over [Config#batchSize].
     * @param names The names or UUIDs of the items to fetch.
     * @param url The url.
     * @param T The type that the items should be parsed into.
     * @return 
     * */
    suspend inline fun <reified T> getChunked(
        names: List<String>,
        url: String
    ): List<ApiResult<List<T>>> {

        val batches = names.chunked(ConfigHandler.cfg.batchSize)
        val semaphore = Semaphore(3)

        val items = coroutineScope {
            batches.map { batch ->
                async {
                    semaphore.withPermit {
                        postRequest<T>(url, batch)
                    }
                }
            }.awaitAll()
        }

        return items
    }
}