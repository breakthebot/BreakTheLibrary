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

import org.breakthebot.breakthelibrary.models.Nation
import org.breakthebot.breakthelibrary.models.Reference
import org.breakthebot.breakthelibrary.models.Resident
import org.breakthebot.breakthelibrary.models.StaffList
import org.breakthebot.breakthelibrary.models.Town
import org.breakthebot.breakthelibrary.network.ApiResult
import org.breakthebot.breakthelibrary.network.Fetch
import org.breakthebot.breakthelibrary.utils.Endpoints
import org.breakthebot.breakthelibrary.utils.SerializableUUID

object TownyAPI {

    suspend fun getAllPlayers(): ApiResult<Resident> = Fetch.getRequest(Endpoints.PLAYERS)
    suspend fun getAllTowns(): ApiResult<Reference> = Fetch.getRequest(Endpoints.TOWNS)
    suspend fun getAllNations(): ApiResult<Reference> = Fetch.getRequest(Endpoints.NATIONS)

    suspend fun getPlayer(name: String): ApiResult<Resident> = Fetch.postRequestItem(Endpoints.PLAYERS, name)

    suspend fun getPlayerDiscord(names: List<String>): List<String?> {
        return when (
            val resp = Fetch.postRequest<Resident>(Endpoints.PLAYERS, names)
        ) {
            is ApiResult.Success -> {
                resp.data.map { it.discord }
            }
            is ApiResult.Error -> {
                listOf(resp.message)
            }
        }

    }

    suspend fun getPlayers(names: List<String>): List<ApiResult<List<Resident>>?> = Fetch.getChunked(names, Endpoints.PLAYERS)


    suspend fun getTown(name: String): ApiResult<Town> = Fetch.postRequestItem(Endpoints.TOWNS, name)

    suspend fun getTowns(names: List<String>): List<ApiResult<List<Town>>?> = Fetch.getChunked(names, Endpoints.TOWNS)


    suspend fun getNation(name: String): ApiResult<Nation> = Fetch.postRequestItem(Endpoints.NATIONS, name)

    suspend fun getNations(names: List<String>): List<ApiResult<List<Nation>>?> = Fetch.getChunked(names, Endpoints.NATIONS)

    suspend fun getStaff(): List<SerializableUUID>? {
        return when (val staff = Fetch.getRequest<StaffList?>(Endpoints.STAFF)) {
            is ApiResult.Success -> {
                staff.data?.allStaff()
            }
            else -> null
        }
    }
}