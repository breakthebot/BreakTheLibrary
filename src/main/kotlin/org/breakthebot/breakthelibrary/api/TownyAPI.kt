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

import org.breakthebot.breakthelibrary.api.interfaces.ITownyAPI
import org.breakthebot.breakthelibrary.models.*
import org.breakthebot.breakthelibrary.network.ApiClient
import org.breakthebot.breakthelibrary.utils.Endpoints
import org.breakthebot.breakthelibrary.utils.SerializableUUID

object TownyAPI : ITownyAPI{

    override suspend fun getAllPlayers(): APIResult<List<Reference>> = ApiClient.getRequest(Endpoints.PLAYERS)
    override suspend fun getAllTowns(): APIResult<List<Reference>> = ApiClient.getRequest(Endpoints.TOWNS)
    override suspend fun getAllNations(): APIResult<List<Reference>> = ApiClient.getRequest(Endpoints.NATIONS)

    override suspend fun getPlayer(name: String): APIResult<Resident> = ApiClient.postRequestItem(Endpoints.PLAYERS, name)

    /**
     * Retrieve the discord user of a player if they are linked.
     * */
    override suspend fun getPlayerDiscord(name: String): APIResult<String> = ApiClient.postRequestItem<Resident>(Endpoints.PLAYERS, name).mapSuccess { it.discord!! }

    override suspend fun getPlayers(names: List<String>): List<APIResult<List<Resident>>> = ApiClient.getChunked(names, Endpoints.PLAYERS)

    override suspend fun getTown(name: String): APIResult<Town> = ApiClient.postRequestItem(Endpoints.TOWNS, name)

    override suspend fun getTowns(names: List<String>): List<APIResult<List<Town>>> = ApiClient.getChunked(names, Endpoints.TOWNS)

    override suspend fun getNation(name: String): APIResult<Nation> = ApiClient.postRequestItem(Endpoints.NATIONS, name)

    override suspend fun getNations(names: List<String>): List<APIResult<List<Nation>>> = ApiClient.getChunked(names, Endpoints.NATIONS)

}