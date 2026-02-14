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

import kotlinx.coroutines.future.await
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.serializer
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

object Fetch {
    val json: Json = Json { ignoreUnknownKeys = true; classDiscriminator = "classDiscriminator" }
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
    suspend inline fun <reified T> getRequest(url: String): T {
        val request = HttpRequest.newBuilder()
            .uri(URI(url))
            .header("Content-Type", "application/json")
            .build()
        val response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).await()
        return parseString<T>(response.body())
    }

    /** Send a request with a request.
     * @param url The url to send the request to.
     * @param body The request body.
     * */
    suspend inline fun <reified T> postRequest(url: String, body: String): T {
       val request = HttpRequest.newBuilder()
           .uri(URI(url))
           .header("Content-Type", "application/json")
           .POST(HttpRequest.BodyPublishers.ofString(body))
           .build()
       val response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).await()
       return parseString<T>(response.body())
   }

    /** Send a request with a json payload without parsing to str.
     * @param url The url to send the request to.
     * @param body The json body.
     * */
    suspend inline fun <reified T> postRequest(url: String, body: JsonObject): T = postRequest<T>(url, body.toString())

    /** Fetch multiple items easily.
     * @param url The url to send the req to
     * @param body A list of uuids or names to fetch from the specified endpoint.
     * */
    suspend inline fun <reified T> postRequest(url: String, body: List<String>): T {
       val uuids = body.toString().removePrefix("[").removeSuffix("]").split(",").map { it.trim().removeSurrounding("\"") }
       val jsonBody = buildJsonObject {
           put("query", JsonArray(uuids.map { JsonPrimitive(it) }))
       }
       return postRequest<T>(url, jsonBody.toString())
   }
}