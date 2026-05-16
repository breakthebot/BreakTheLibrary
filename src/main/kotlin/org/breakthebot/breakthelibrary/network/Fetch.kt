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
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.serializer
import org.breakthebot.breakthelibrary.BreakTheLibrary
import org.breakthebot.breakthelibrary.utils.ConfigHandler
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


sealed class ApiResult<out T> {
    data class Success<T>(
        val data: T,
        val statusCode: Int
    ) : ApiResult<T>()

    data class Error(
        val message: String,
        val statusCode: Int?,
    ) : ApiResult<Nothing>()
}

fun <T> ApiResult<T>.getOrNull(): T?{
    return when (this) {
        is ApiResult.Success<T> -> this.data
        else -> null
    }
}

object Fetch {
    val json = BreakTheLibrary.json
    val client: HttpClient = HttpClient.newHttpClient()

    /** Parse a string into a specified T type.
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
     * */
    suspend inline fun <reified T> getRequest(url: String): ApiResult<T> {
        val request = HttpRequest.newBuilder().apply {
            uri(URI(url))
            header("Content-Type", "application/json")
        }.build()
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
    }

    /** Send a request with a request.
     * @param url The url to send the request to.
     * @param body The request body.
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

        val response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).await()
        val body = response.body()
        if (response.statusCode() != 200) {
            return ApiResult.Error(
                // In the context of towny apis this will almost always be the error message.
                body,
                response.statusCode()
            )
        }
        // If body is [], then query is 404 but api doesnt return it.
        if (body.length == 2) {
            return ApiResult.Error(
                "Not found",
                404
            )
        }
        return ApiResult.Success(
           parseString<T>(body),
           200
       )
    }

    /** Send a request with a JSON payload without parsing to str.
     * @param url The url to send the request to.
     * @param body The JSON body.
     * */
    suspend inline fun <reified T> postRequest(
        url: String,
        body: JsonObject
    ): ApiResult<T> = postRequest<T>(url, body.toString())

    /** Fetch multiple items easily.
     * @param T The type of the objects you want to fetch.
     * @param url The url to send the req to
     * @param body A list of UUID's or names to fetch from the specified endpoint.
     * */
    suspend inline fun <reified T> postRequest(
        url: String,
        body: List<String>
    ): ApiResult<List<T>> {
       val uuids = body.toString().removePrefix("[").removeSuffix("]").split(",").map { it.trim().removeSurrounding("\"") }
       val jsonBody = buildJsonObject {
           put("query", JsonArray(uuids.map { JsonPrimitive(it) }))
       }
       return postRequest(url, jsonBody.toString())
    }


    /** Fetch multiple items easily.
     * @param T The type of the objects you want to fetch.
     * @param url The url to send the req to
     * @param body A list of UUID's or names to fetch from the specified endpoint.
     * */
    suspend inline fun <reified T> postRequestItem(
        url: String,
        body: String
    ): ApiResult<T> {
        val jsonBody = buildJsonObject {
            put("query", JsonArray(listOf(JsonPrimitive(body))))
        }

        return postRequest(url, jsonBody.toString())
    }

    suspend inline fun <reified T> getChunked(
        names: List<String>,
        url: String
    ): List<ApiResult<List<T>>?> {
        if (names.size < ConfigHandler.cfg.batchSize) {
            return listOf(postRequest(url, names))
        }

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