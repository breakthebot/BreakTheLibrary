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

import org.breakthebot.breakthelibrary.models.ApiResult
import org.breakthebot.breakthelibrary.models.Nation
import org.breakthebot.breakthelibrary.models.Reference
import org.breakthebot.breakthelibrary.models.Resident
import org.breakthebot.breakthelibrary.models.StaffList
import org.breakthebot.breakthelibrary.models.Town
import org.breakthebot.breakthelibrary.models.mapSuccess
import org.breakthebot.breakthelibrary.network.ApiClient
import org.breakthebot.breakthelibrary.utils.Endpoints
import org.breakthebot.breakthelibrary.utils.SerializableUUID

object TownyAPI {

    suspend fun getAllPlayers(): ApiResult<List<Reference>> = ApiClient.getRequest(Endpoints.PLAYERS)
    suspend fun getAllTowns(): ApiResult<List<Reference>> = ApiClient.getRequest(Endpoints.TOWNS)
    suspend fun getAllNations(): ApiResult<List<Reference>> = ApiClient.getRequest(Endpoints.NATIONS)

    suspend fun getPlayer(name: String): ApiResult<Resident> = ApiClient.postRequestItem(Endpoints.PLAYERS, name)

    /**
     * Retrieve the discord user of a player if they are linked.
     * */
    suspend fun getPlayerDiscord(names: String): ApiResult<String?> = ApiClient.postRequestItem<Resident>(Endpoints.PLAYERS, names).mapSuccess { it.discord }

    suspend fun getPlayers(names: List<String>): List<ApiResult<List<Resident>>> = ApiClient.getChunked(names, Endpoints.PLAYERS)

    suspend fun getTown(name: String): ApiResult<Town> = ApiClient.postRequestItem(Endpoints.TOWNS, name)

    suspend fun getTowns(names: List<String>): List<ApiResult<List<Town>>> = ApiClient.getChunked(names, Endpoints.TOWNS)

    suspend fun getNation(name: String): ApiResult<Nation> = ApiClient.postRequestItem(Endpoints.NATIONS, name)

    suspend fun getNations(names: List<String>): List<ApiResult<List<Nation>>> = ApiClient.getChunked(names, Endpoints.NATIONS)

    suspend fun getStaff(): ApiResult<List<SerializableUUID>> = ApiClient.getRequest<StaffList>(Endpoints.STAFF).mapSuccess { it.allStaff() }
}