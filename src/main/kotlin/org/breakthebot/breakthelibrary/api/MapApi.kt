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
import kotlinx.serialization.json.encodeToJsonElement
import org.breakthebot.breakthelibrary.models.Location
import org.breakthebot.breakthelibrary.models.MapReturn
import org.breakthebot.breakthelibrary.models.NearbyItem
import org.breakthebot.breakthelibrary.models.PlayerMapReturn
import org.breakthebot.breakthelibrary.models.Reference
import org.breakthebot.breakthelibrary.network.ApiResult
import org.breakthebot.breakthelibrary.network.Fetch
import org.breakthebot.breakthelibrary.utils.Endpoints

object MapApi {

    suspend fun getVisiblePlayers(): List<PlayerMapReturn>? {
        return when (val resp = Fetch.getRequest<MapReturn>(Endpoints.MAP)) {
            is ApiResult.Success<MapReturn> -> {
                resp.data.players
            }
            else -> null
        }
    }

    suspend fun getLocation(query: List<Pair<Double, Double>>): ApiResult<List<Location>> {
        val body = buildJsonObject {
            put("query", JsonArray(query.map { (x, y) ->
                JsonArray(
                    listOf(
                        JsonPrimitive(x), JsonPrimitive(y)
                    )
                )
            }))
        }

        return Fetch.postRequest<List<Location>>(Endpoints.LOCATION, body)
    }

    suspend fun getNearby(query: List<NearbyItem>): ApiResult<List<Reference>?> {
        val body = buildJsonObject {
            put("query", JsonArray(listOf(Fetch.json.encodeToJsonElement(query))))
        }
        return Fetch.postRequest<List<Reference>?>(Endpoints.NEARBY, body.toString())
    }
}