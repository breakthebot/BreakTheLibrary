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
import kotlinx.serialization.serializer
import org.breakthebot.breakthelibrary.BreakTheLibrary
import org.breakthebot.breakthelibrary.api.APIClient.future
import org.breakthebot.breakthelibrary.models.APIResult
import org.breakthebot.breakthelibrary.models.Location
import org.breakthebot.breakthelibrary.models.MapReturn
import org.breakthebot.breakthelibrary.models.NearbyItem
import org.breakthebot.breakthelibrary.models.PlayerMapReturn
import org.breakthebot.breakthelibrary.models.Reference
import org.breakthebot.breakthelibrary.utils.Endpoints
import java.util.concurrent.CompletableFuture

open class BaseMapAPI(
    val apiClient: BaseAPIClient,
) {
    open suspend fun getVisiblePlayers(): List<PlayerMapReturn>? = apiClient
        .getRequest(Endpoints.MAP_API, MapReturn.serializer())
        .getOrNull()
        ?.players

    /**
     * Query the location api.
     * @param query The list of coordinates to query.
     * */
    open suspend fun getLocation(query: Collection<Pair<Double, Double>>): APIResult<List<Location>> {
        val body = buildJsonObject {
            put(
                "query",
                JsonArray(
                    query.map { (x, y) ->
                        JsonArray(
                            listOf(
                                JsonPrimitive(x),
                                JsonPrimitive(y),
                            ),
                        )
                    },
                ),
            )
        }
        return apiClient.postRequest<List<Location>>(Endpoints.LOCATION, body)
    }

    open suspend fun getNearby(query: NearbyItem): APIResult<List<Reference>?> {
        val body =
            buildJsonObject {
                put("query", JsonArray(listOf(BreakTheLibrary.json.encodeToJsonElement(query))).jsonArray)
            }

        return apiClient.postRequest(
            Endpoints.NEARBY,
            body.toString(),
            serializer<List<List<Reference>?>?>()
        ).mapSuccess { it?.firstOrNull() }
    }

    fun getVisiblePlayersJava(): CompletableFuture<List<PlayerMapReturn>?> = future { getVisiblePlayers() }

    fun getLocationJava(query: Collection<Pair<Double, Double>>): CompletableFuture<APIResult<List<Location>>> = future { getLocation(query) }

    fun getLocationJava(query: Pair<Double, Double>): CompletableFuture<APIResult<Location>> = future {
        getLocation(
            listOf(query),
        ).mapSuccess { it[0] }
    }

    fun getNearbyJava(query: NearbyItem): CompletableFuture<APIResult<List<Reference>?>> = future {
        getNearby(query)
    }
}

object MapAPI : BaseMapAPI(APIClient)
