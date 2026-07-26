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

import kotlinx.serialization.serializer
import org.breakthebot.breakthelibrary.api.APIClient.future
import org.breakthebot.breakthelibrary.models.APIResult
import org.breakthebot.breakthelibrary.models.Nation
import org.breakthebot.breakthelibrary.models.Reference
import org.breakthebot.breakthelibrary.models.Resident
import org.breakthebot.breakthelibrary.models.Town
import org.breakthebot.breakthelibrary.utils.Endpoints
import java.util.concurrent.CompletableFuture

open class BaseTownyAPI(val apiClient: BaseAPIClient) {
    open suspend fun getAllPlayers(): APIResult<List<Reference>> = apiClient.getRequest(Endpoints.PLAYERS, serializer<List<Reference>>())

    open suspend fun getAllTowns(): APIResult<List<Reference>> = apiClient.getRequest(Endpoints.TOWNS, serializer<List<Reference>>())

    open suspend fun getAllNations(): APIResult<List<Reference>> = apiClient.getRequest(Endpoints.NATIONS, serializer<List<Reference>>())

    open suspend fun getPlayer(name: String): APIResult<Resident> = apiClient.postRequestItem(Endpoints.PLAYERS, name)

    /**
     * Retrieve the discord user of a player if they are linked.
     * */
    open suspend fun getPlayerDiscord(name: String): APIResult<String> = apiClient.postRequestItem<Resident>(Endpoints.PLAYERS, name).mapSuccess { it.discord!! }

    open suspend fun getPlayers(names: Collection<String>): List<APIResult<List<Resident>>> = apiClient.getChunked(names, Endpoints.PLAYERS, serializer<List<Resident>>())

    open suspend fun getTown(name: String): APIResult<Town> = apiClient.postRequestItem(Endpoints.TOWNS, name)

    open suspend fun getTowns(names: Collection<String>): List<APIResult<List<Town>>> = apiClient.getChunked(names, Endpoints.TOWNS, serializer<List<Town>>())

    open suspend fun getNation(name: String): APIResult<Nation> = apiClient.postRequestItem(Endpoints.NATIONS, name)

    open suspend fun getNations(names: Collection<String>): List<APIResult<List<Nation>>> = apiClient.getChunked(names, Endpoints.NATIONS, serializer<List<Nation>>())

    fun getAllPlayersJava(): CompletableFuture<APIResult<List<Reference>>> = future { getAllPlayers() }

    fun getAllTownsJava(): CompletableFuture<APIResult<List<Reference>>> = future { getAllTowns() }

    fun getAllNationsJava(): CompletableFuture<APIResult<List<Reference>>> = future { getAllNations() }

    fun getPlayerJava(name: String): CompletableFuture<APIResult<Resident>> = future { getPlayer(name) }

    fun getPlayerDiscordJava(name: String): CompletableFuture<APIResult<String>> = future { getPlayerDiscord(name) }

    fun getPlayersJava(names: Collection<String>): CompletableFuture<List<APIResult<List<Resident>>>> = future { getPlayers(names) }

    fun getTownJava(name: String): CompletableFuture<APIResult<Town>> = future { getTown(name) }

    fun getTownsJava(names: Collection<String>): CompletableFuture<List<APIResult<List<Town>>>> = future { getTowns(names) }

    fun getNationJava(name: String): CompletableFuture<APIResult<Nation>> = future { getNation(name) }

    fun getNationsJava(names: Collection<String>): CompletableFuture<List<APIResult<List<Nation>>>> = future { getNations(names) }
}

object TownyAPI : BaseTownyAPI(APIClient)
