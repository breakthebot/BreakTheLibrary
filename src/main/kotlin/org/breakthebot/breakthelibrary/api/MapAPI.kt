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
import kotlinx.serialization.json.jsonArray
import org.breakthebot.breakthelibrary.models.ApiResult
import org.breakthebot.breakthelibrary.models.Location
import org.breakthebot.breakthelibrary.models.MapReturn
import org.breakthebot.breakthelibrary.models.NearbyItem
import org.breakthebot.breakthelibrary.models.PlayerMapReturn
import org.breakthebot.breakthelibrary.models.Reference
import org.breakthebot.breakthelibrary.network.ApiClient
import org.breakthebot.breakthelibrary.network.ApiClient.future
import org.breakthebot.breakthelibrary.utils.Endpoints
import java.util.concurrent.CompletableFuture

object MapAPI {

    suspend fun getVisiblePlayers(): List<PlayerMapReturn>? = ApiClient.getRequest<MapReturn>(Endpoints.MAP)
        .getOrNull()
        ?.players

    /**
     * Query the location api.
     * @param query The list of coordinates to query.
     * */
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
        return ApiClient.postRequest<List<Location>>(Endpoints.LOCATION, body)
    }

    suspend fun getNearby(query: NearbyItem): ApiResult<List<Reference>?> {
        val body = buildJsonObject {
            put("query", JsonArray(ApiClient.json.encodeToJsonElement(listOf(query)).jsonArray))
        }
        return ApiClient.postRequest<List<List<Reference>?>?>(Endpoints.NEARBY, body.toString()).mapSuccess { it?.first() }
    }

    fun getVisiblePlayersJava(): CompletableFuture<List<PlayerMapReturn>?> =  future { getVisiblePlayers() }

    fun getLocationJava(query: List<Pair<Double, Double>>): CompletableFuture<ApiResult<List<Location>>> = future { getLocation(query) }

    fun getLocationJava(query: Pair<Double, Double>): CompletableFuture<ApiResult<Location>> = future { getLocation(listOf(query)).mapSuccess { it[0] } }

    fun getNearbyJava(query: NearbyItem): CompletableFuture<ApiResult<List<Reference>?>> = future { getNearby(query) }
}