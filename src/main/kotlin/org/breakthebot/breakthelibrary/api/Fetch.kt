/*
 * This file is part of breakthelibrary.
 *
 * breakthelibrary is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * breakthelibrary is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with breakthelibrary. If not, see <https://www.gnu.org/licenses/>.
 */
package org.breakthebot.breakthelibrary.api

import kotlinx.coroutines.future.await
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

object Fetch {
    val json: Json = Json { ignoreUnknownKeys = true}
    val client: HttpClient = HttpClient.newHttpClient()

    /** Parse a string into a specified T type.
     * @param body The string to parse into the T type. */
    inline fun <reified T> parseString(body: String): T {
        return if (T::class == String::class) {
            body as T
        } else { json.decodeFromString<T>(body) }
    }

    /** Sends a get request.
     * @param url The url to send the request to.*/
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
    suspend inline fun <reified T> postRequest(url: String, body: JsonArray): T = postRequest<T>(url, body.toString())

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