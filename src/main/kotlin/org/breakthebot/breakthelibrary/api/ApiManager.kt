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

import org.breakthebot.breakthelibrary.objects.DiscordResponse
import org.breakthebot.breakthelibrary.objects.ErrorEnum
import org.breakthebot.breakthelibrary.objects.ErrorObject
import org.breakthebot.breakthelibrary.objects.Location
import org.breakthebot.breakthelibrary.objects.MapReturn
import org.breakthebot.breakthelibrary.objects.MysteryMaster
import org.breakthebot.breakthelibrary.objects.Nation
import org.breakthebot.breakthelibrary.objects.NearbyItem
import org.breakthebot.breakthelibrary.objects.Reference
import org.breakthebot.breakthelibrary.objects.Resident
import org.breakthebot.breakthelibrary.objects.Result;
import org.breakthebot.breakthelibrary.objects.ServerInfo
import org.breakthebot.breakthelibrary.objects.StaffList
import org.breakthebot.breakthelibrary.objects.Town
import org.breakthebot.breakthelibrary.objects.getOrNull
import org.breakthebot.breakthelibrary.objects.getOrThrow
import org.breakthebot.breakthelibrary.objects.mapError
import org.breakthebot.breakthelibrary.objects.mapSuccess
import org.breakthebot.breakthelibrary.utils.SerializableUUID
import java.util.UUID
import java.lang.Exception
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlinx.coroutines.future.await
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import org.breakthebot.breakthelibrary.objects.DiscordPayload

object ApiManager : IApiManager {
    val json: Json =  Json {
        ignoreUnknownKeys = true
    }

    val client: HttpClient = HttpClient.newHttpClient()

    val apiUrl = "https://api.earthmc.net/v3/aurora"
    val staffRepoURL = "https://raw.githubusercontent.com/veyronity/staff/master/staff.json"

    val mapURL = "https://map.earthmc.net/tiles/players.json"

    val urls = mapOf(
        "town" to "$apiUrl/towns",
        "nation" to "$apiUrl/nations",
        "player" to "$apiUrl/players",
        "nearby" to "$apiUrl/nearby",
        "location" to "$apiUrl/location",
        "discord" to "$apiUrl/discord",
        "quarters" to "$apiUrl/quarters",
        "mm" to "$apiUrl/mm",
        "server" to "$apiUrl/servers",
        "map" to mapURL
    )

    /**
     * Sends a get request request.
     * @generic T the type to infer the response into.
     * @param endpoint The endpoint to send the request to.
     */
    internal suspend inline fun <reified T> getRequest(url: String): Result<T?, ErrorObject?> {

        val request = HttpRequest.newBuilder()
            .uri(URI(formatUrl(url)))
            .header("Content-Type", "application/json")
            .build()

        return try {
            val response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).await()
            val body = response.body()

            val result: T? = if (T::class == String::class) {
                body as T
            } else {
                json.decodeFromString<T>(body)
            }

            Result.ok(result)
        } catch (e: Exception) {
            Result.err(ErrorObject(ErrorEnum.Unknown, e.message!!))
        }
    }

    /**
     * Sends a post request.
     * @generic T the type to infer the response into.
     * @param endpoint The endpoint to send the request to.
     * @param body The request body.
     */
    internal suspend inline fun <reified T> postRequest(endpoint: String, body: String): Result<T?, ErrorObject?> {
        val url = urls[endpoint] ?: return Result.err(ErrorObject(ErrorEnum.NotFound, "endpoint $endpoint"))

        val request = HttpRequest.newBuilder()
            .uri(URI(formatUrl(url)))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build()

        return try {
            val response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).await()
            val body = response.body()

            val result: T? = if (T::class == String::class) {
                body as T
            } else {
                json.decodeFromString<T>(body)
            }

            Result.ok(result)
        } catch (e: Exception) {
            Result.err(ErrorObject(ErrorEnum.Unknown, e.message!!))
        }
    }

    /**
     * Sends a post request.
     * @generic T the type to infer the response into.
     * @param endpoint The endpoint to send the request to.
     * @param body The request body.
     */
    internal suspend inline fun <reified T> postRequest(endpoint: String, body: JsonObject): Result<T?, ErrorObject?> = postRequest<T>(endpoint, body.toString())

    /**
     * Parse a list of uuids in a string into a json array.
     * */
    fun parseUUIDs(body: String): Result<JsonObject?, ErrorObject?> {
        try {
            val uuids = body.removePrefix("[").removeSuffix("]").split(",").map { it.trim().removeSurrounding("\"") }
            return Result.ok(buildJsonObject {
                put("query", JsonArray(uuids.map { JsonPrimitive(it) }))
            })
        } catch (e : kotlin.Exception) {
            return Result.err(ErrorObject(ErrorEnum.Unknown, e.message!!))
        }
    }

    internal suspend fun <T> getAll(item: String): Result<List<T>?, ErrorObject?> {
         try {
            val resp = getRequest<List<T>>(urls[item] ?: return Result.err(ErrorObject(ErrorEnum.NotFound, "")))
            return Result.ok(resp.getOrThrow())

        } catch (e: kotlin.Exception) {
            return Result.err(ErrorObject(ErrorEnum.Unknown, e.message!!))
        }
    }

    internal suspend inline fun <reified T> getItem(item: String, name: List<String>): Result<T?, ErrorObject?> = try {
            val uuids =   parseUUIDs(name.toString()).getOrNull() ?: return Result.err(ErrorObject(ErrorEnum.Unknown, "failed to parse uuids."))
            postRequest<T>(endpoint = item, body = uuids)
        } catch (e: kotlin.Exception) {
            Result.err(ErrorObject(ErrorEnum.Unknown, e.message!!))
        }


    override suspend fun getTowns(): Result<List<Reference>?, ErrorObject?> = getAll(urls["town"]!!)

    override suspend fun getTowns(names: List<String>): Result<List<Town>?, ErrorObject?> = try {
            getItem<List<Town>>("towns", names).mapError { e -> ErrorObject(ErrorEnum.Unknown, e?.message ?: "Unable to fetch towns.") }
    } catch (e: kotlin.Exception) { Result.err(ErrorObject(ErrorEnum.Unknown, e.message!!)) }

    override suspend fun getTowns(names: Array<UUID>): Result<List<Town>?, ErrorObject?> = getTowns(names.map { it.toString() })

    override suspend fun getTown(name: String): Result<Town?, ErrorObject?> = getTowns(listOf(name)).mapSuccess { it?.get(0) }

    override suspend fun getTown(name: UUID): Result<Town?, ErrorObject?> = getTowns(arrayOf(name)).mapSuccess { it?.get(0) }

    override suspend fun getNations(): Result<List<Reference>?, ErrorObject?> = getAll(urls["nation"]!!)

    override suspend fun getNations(names: List<String>): Result<List<Nation>?, ErrorObject?> = try {
        getItem<List<Nation>>("nations", names).mapError { e -> ErrorObject(ErrorEnum.Unknown, e?.message ?: "Unable to fetch nations.") }
    } catch (e: kotlin.Exception) { Result.err(ErrorObject(ErrorEnum.Unknown, e.message!!)) }

    override suspend fun getNations(names: Array<UUID>): Result<List<Nation>?, ErrorObject?> = getNations(names.map { it.toString() })

    override suspend fun getNation(name: String): Result<Nation?, ErrorObject?> = getNations(listOf(name)).mapSuccess { it?.get(0) }

    override suspend fun getNation(name: UUID): Result<Nation?, ErrorObject?> = getNations(listOf(name.toString())).mapSuccess { it?.get(0) }

    override suspend fun getPlayers(): Result<List<Reference>?, ErrorObject?> = getAll(urls["player"]!!)

    override suspend fun getPlayers(names: List<String>): Result<List<Resident>?, ErrorObject?> = try {
        getItem<List<Resident>>("players", names).mapError { e -> ErrorObject(ErrorEnum.Unknown, e?.message ?: "Unable to fetch players.") }
    } catch (e: kotlin.Exception) { Result.err(ErrorObject(ErrorEnum.Unknown, e.message!!)) }

    override suspend fun getPlayers(names: Array<UUID>): Result<List<Resident>?, ErrorObject?> = getPlayers(names.map { it.toString() })

    override suspend fun getPlayer(name: String): Result<Resident?, ErrorObject?> = getPlayers(listOf(name)).mapSuccess { it?.get(0) }

    override suspend fun getPlayer(name: UUID): Result<Resident?, ErrorObject?> = getPlayers(arrayOf(name)).mapSuccess { it?.get(0) }

    override suspend fun getServer(): Result<ServerInfo?, ErrorObject?> = getAll<ServerInfo>("").mapSuccess { it?.get(0) }

    override suspend fun getStaff(): Result<List<SerializableUUID>?, ErrorObject?> = try {
        getAll<StaffList>(staffRepoURL).mapSuccess { it?.get(0)?.allStaff() }
    } catch (e: kotlin.Exception) { Result.err(ErrorObject(ErrorEnum.Unknown, e.message!!)) }

    override suspend fun getNearby(query: NearbyItem): Result<List<Reference>?, ErrorObject?> = try {
        postRequest<List<Reference>>("nearby", query.toString())
    } catch (e: kotlin.Exception) {
        Result.err(ErrorObject(ErrorEnum.Unknown, e.message!!))
    }

    override suspend fun getVisiblePlayers(): Result<List<MapReturn>?, ErrorObject?> = getRequest<List<MapReturn>>(urls["map"]!!)

    override suspend fun getMysteryMaster(): Result<List<MysteryMaster>?, ErrorObject?> = getRequest<List<MysteryMaster>>(urls["mm"]!!)

    override suspend fun getLocation(query: List<Pair<Int, Int>>): Result<List<Location>?, ErrorObject?> {
        val body = buildJsonObject {
            put("query", JsonArray(query.map { (x, y) -> JsonArray(listOf(JsonPrimitive(x), JsonPrimitive(y))) }))
        }

        return postRequest<List<Location>>(urls["location"]!!, body)
    }

    override suspend fun getDiscord(query: List<DiscordPayload>): Result<List<DiscordResponse>?, ErrorObject?> {
        val body = buildJsonObject {
            put("query", JsonArray(query.map { Json.encodeToJsonElement(it).jsonObject }))
        }
        return postRequest<List<DiscordResponse>>(urls["discord"]!!, body)
    }

    fun formatUrl(url: String): String {
        if (url.isEmpty()) return url
        val protocolEnd = url.indexOf("://")
        if (protocolEnd == -1) return url
        val protocol = url.take(protocolEnd + 3)
        var rest = url.substring(protocolEnd + 3)
        rest = rest.replace("/{2,}".toRegex(), "/")
        if (rest.endsWith("/") && rest.length > 1) {
            rest = rest.dropLast(1)
        }
        return protocol + rest
    }
}