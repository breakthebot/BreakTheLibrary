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

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import org.breakthebot.breakthelibrary.models.Location
import org.breakthebot.breakthelibrary.models.MapReturn
import org.breakthebot.breakthelibrary.models.PlayerMapReturn
import org.breakthebot.breakthelibrary.network.Fetch.getRequest
import org.breakthebot.breakthelibrary.network.Fetch.postRequest
import org.breakthebot.breakthelibrary.utils.Endpoints

object LocationAPI {

    suspend fun getLocation(query: List<Pair<Double, Double>>): List<Location>? {
        val body = buildJsonObject {
            put("query", JsonArray(query.map { (x, y) -> JsonArray(
                listOf(JsonPrimitive(x), JsonPrimitive(y)
                ))
            }))
        }

        return postRequest<List<Location>?>(Endpoints.LOCATION, body)
    }

    suspend fun getVisiblePlayers(): List<PlayerMapReturn>? = getRequest<MapReturn?>(Endpoints.MAP)?.players

}