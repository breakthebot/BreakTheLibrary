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

import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.serializer
import org.breakthebot.breakthelibrary.api.APIClient.future
import org.breakthebot.breakthelibrary.models.APIResult
import org.breakthebot.breakthelibrary.models.AllianceFilter
import org.breakthebot.breakthelibrary.models.AllianceModel
import org.breakthebot.breakthelibrary.models.AllianceRanking
import org.breakthebot.breakthelibrary.models.AllianceStats
import org.breakthebot.breakthelibrary.models.Nation
import org.breakthebot.breakthelibrary.models.Reference
import org.breakthebot.breakthelibrary.models.Resident
import org.breakthebot.breakthelibrary.models.Template
import org.breakthebot.breakthelibrary.models.Town
import org.breakthebot.breakthelibrary.utils.Endpoints
import java.util.concurrent.CompletableFuture
import kotlin.uuid.Uuid

open class BaseTownyAPI(val apiClient: BaseAPIClient) {
    open suspend fun getAllPlayers(): APIResult<List<Reference>> = apiClient.getRequest(Endpoints.PLAYERS, serializer<List<Reference>>())

    open suspend fun getAllTowns(): APIResult<List<Reference>> = apiClient.getRequest(Endpoints.TOWNS, serializer<List<Reference>>())

    open suspend fun getAllNations(): APIResult<List<Reference>> = apiClient.getRequest(Endpoints.NATIONS, serializer<List<Reference>>())

    open suspend fun getAllAlliances(): APIResult<Map<String, Uuid>> = apiClient.getRequest(Endpoints.ALLIANCES_API, serializer<Map<String, String>>()).mapSuccess {
        val map = mutableMapOf<String, Uuid>()
        it.forEach { (k, v) ->
            map[k] = Uuid.parse(v)
        }
        map
    }

    open suspend fun getPlayer(name: String): APIResult<Resident> = apiClient.postRequestItem(Endpoints.PLAYERS, name)

    /**
     * Retrieve the discord user of a player if they are linked.
     * */
    open suspend fun getPlayerDiscord(name: String): APIResult<String> = apiClient.postRequestItem<Resident>(Endpoints.PLAYERS, name).mapSuccess { it.discord!! }

    open suspend fun getPlayers(names: Collection<String>): List<APIResult<List<Resident>>> = apiClient.getChunked(names, Endpoints.PLAYERS, serializer<List<Resident>>())

    open suspend fun getPlayers(names: Collection<String>, template: Template): List<APIResult<List<Resident>>> = apiClient.getChunked(Endpoints.PLAYERS, names, template)

    open suspend fun getTown(name: String): APIResult<Town> = apiClient.postRequestItem(Endpoints.TOWNS, name)

    open suspend fun getTowns(names: Collection<String>): List<APIResult<List<Town>>> = apiClient.getChunked(names, Endpoints.TOWNS, serializer<List<Town>>())

    open suspend fun getTowns(names: Collection<String>, template: Template): List<APIResult<List<Town>>> = apiClient.getChunked(Endpoints.TOWNS, names, template)

    open suspend fun getNation(name: String): APIResult<Nation> = apiClient.postRequestItem(Endpoints.NATIONS, name)

    open suspend fun getNations(names: Collection<String>): List<APIResult<List<Nation>>> = apiClient.getChunked(names, Endpoints.NATIONS, serializer<List<Nation>>())

    open suspend fun getNations(names: Collection<String>, template: Template): List<APIResult<List<Nation>>> = apiClient.getChunked(Endpoints.NATIONS, names, template)

    open suspend fun getAlliance(name: String): APIResult<AllianceModel> {
        val body = buildJsonObject { put("name", JsonPrimitive(name)) }
        return apiClient.postRequest(Endpoints.ALLIANCES_API, body)
    }

    open suspend fun getAllianceStats(name: String): APIResult<AllianceStats> {
        val body = buildJsonObject { put("name", JsonPrimitive(name)) }
        return apiClient.postRequest(Endpoints.ALLIANCES_STATS_API, body)
    }

    open suspend fun getTopAlliances(filter: AllianceFilter): APIResult<List<AllianceRanking>> {
        val body = buildJsonObject { put("filter", JsonPrimitive(filter.toString())) }
        return apiClient.postRequest(Endpoints.ALLIANCES_TOP_API, body)
    }

    fun getAllPlayersJava(): CompletableFuture<APIResult<List<Reference>>> = future { getAllPlayers() }

    fun getAllTownsJava(): CompletableFuture<APIResult<List<Reference>>> = future { getAllTowns() }

    fun getAllNationsJava(): CompletableFuture<APIResult<List<Reference>>> = future { getAllNations() }

    fun getAllAlliancesJava(): CompletableFuture<APIResult<Map<String, Uuid>>> = future { getAllAlliances() }

    fun getPlayerJava(name: String): CompletableFuture<APIResult<Resident>> = future { getPlayer(name) }

    fun getPlayerDiscordJava(name: String): CompletableFuture<APIResult<String>> = future { getPlayerDiscord(name) }

    fun getPlayersJava(names: Collection<String>): CompletableFuture<List<APIResult<List<Resident>>>> = future { getPlayers(names) }

    fun getTownJava(name: String): CompletableFuture<APIResult<Town>> = future { getTown(name) }

    fun getTownsJava(names: Collection<String>): CompletableFuture<List<APIResult<List<Town>>>> = future { getTowns(names) }

    fun getNationJava(name: String): CompletableFuture<APIResult<Nation>> = future { getNation(name) }

    fun getNationsJava(names: Collection<String>): CompletableFuture<List<APIResult<List<Nation>>>> = future { getNations(names) }

    fun getAllianceJava(name: String): CompletableFuture<APIResult<AllianceModel>> = future { getAlliance(name) }

    fun getAllianceStatsJava(name: String): CompletableFuture<APIResult<AllianceStats>> = future { getAllianceStats(name) }

    fun getTopAlliancesJava(filter: AllianceFilter): CompletableFuture<APIResult<List<AllianceRanking>>> = future { getTopAlliances(filter) }
}

object TownyAPI : BaseTownyAPI(APIClient)
