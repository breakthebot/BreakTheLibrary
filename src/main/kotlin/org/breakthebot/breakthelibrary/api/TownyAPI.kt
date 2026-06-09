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

import org.breakthebot.breakthelibrary.models.*
import org.breakthebot.breakthelibrary.network.ApiClient
import org.breakthebot.breakthelibrary.network.ApiClient.future
import org.breakthebot.breakthelibrary.utils.Endpoints
import org.breakthebot.breakthelibrary.utils.SerializableUUID
import java.util.concurrent.CompletableFuture

object TownyAPI {


    suspend fun getAllPlayers(): ApiResult<List<Reference>> = ApiClient.getRequest(Endpoints.PLAYERS)
    suspend fun getAllTowns(): ApiResult<List<Reference>> = ApiClient.getRequest(Endpoints.TOWNS)
    suspend fun getAllNations(): ApiResult<List<Reference>> = ApiClient.getRequest(Endpoints.NATIONS)

    suspend fun getPlayer(name: String): ApiResult<Resident> = ApiClient.postRequestItem(Endpoints.PLAYERS, name)

    /**
     * Retrieve the discord user of a player if they are linked.
     * */
    suspend fun getPlayerDiscord(name: String): ApiResult<String> = ApiClient.postRequestItem<Resident>(Endpoints.PLAYERS, name).mapSuccess { it.discord!! }

    suspend fun getPlayers(names: List<String>): List<ApiResult<List<Resident>>> = ApiClient.getChunked(names, Endpoints.PLAYERS)

    suspend fun getTown(name: String): ApiResult<Town> = ApiClient.postRequestItem(Endpoints.TOWNS, name)

    suspend fun getTowns(names: List<String>): List<ApiResult<List<Town>>> = ApiClient.getChunked(names, Endpoints.TOWNS)

    suspend fun getNation(name: String): ApiResult<Nation> = ApiClient.postRequestItem(Endpoints.NATIONS, name)

    suspend fun getNations(names: List<String>): List<ApiResult<List<Nation>>> = ApiClient.getChunked(names, Endpoints.NATIONS)

    suspend fun getStaff(): ApiResult<List<SerializableUUID>> = ApiClient.getRequest<StaffList>(Endpoints.STAFF).mapSuccess { it.allStaff() }

    // Java API

    fun getAllPlayersJava(): CompletableFuture<ApiResult<List<Reference>>> =
        future { getAllPlayers() }

    fun getAllTownsJava(): CompletableFuture<ApiResult<List<Reference>>> =
        future { getAllTowns() }

    fun getAllNationsJava(): CompletableFuture<ApiResult<List<Reference>>> =
        future { getAllNations() }

    fun getPlayerJava(name: String): CompletableFuture<ApiResult<Resident>> =
        future { getPlayer(name) }

    fun getPlayerDiscordJava(name: String): CompletableFuture<ApiResult<String>> =
        future { getPlayerDiscord(name) }

    fun getPlayersJava(names: List<String>): CompletableFuture<List<ApiResult<List<Resident>>>> =
        future { getPlayers(names) }

    fun getTownJava(name: String): CompletableFuture<ApiResult<Town>> =
        future { getTown(name) }

    fun getTownsJava(names: List<String>): CompletableFuture<List<ApiResult<List<Town>>>> =
        future { getTowns(names) }

    fun getNationJava(name: String): CompletableFuture<ApiResult<Nation>> =
        future { getNation(name) }

    fun getNationsJava(names: List<String>): CompletableFuture<List<ApiResult<List<Nation>>>> =
        future { getNations(names) }

    fun getStaffJava(): CompletableFuture<ApiResult<List<SerializableUUID>>> =
        future { getStaff() }

}