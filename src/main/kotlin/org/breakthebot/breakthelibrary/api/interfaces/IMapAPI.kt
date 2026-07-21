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
package org.breakthebot.breakthelibrary.api.interfaces

import org.breakthebot.breakthelibrary.api.APIClient.future
import org.breakthebot.breakthelibrary.models.APIResult
import org.breakthebot.breakthelibrary.models.Location
import org.breakthebot.breakthelibrary.models.NearbyItem
import org.breakthebot.breakthelibrary.models.PlayerMapReturn
import org.breakthebot.breakthelibrary.models.Reference
import java.util.concurrent.CompletableFuture

interface IMapAPI {
    suspend fun getVisiblePlayers(): List<PlayerMapReturn>?

    suspend fun getLocation(query: Collection<Pair<Double, Double>>): APIResult<List<Location>>

    suspend fun getNearby(query: NearbyItem): APIResult<List<Reference>?>

    fun getVisiblePlayersJava(): CompletableFuture<List<PlayerMapReturn>?>

    fun getLocationJava(query: Collection<Pair<Double, Double>>): CompletableFuture<APIResult<List<Location>>> =
        future { getLocation(query) }

    fun getLocationJava(query: Pair<Double, Double>): CompletableFuture<APIResult<Location>> =
        future {
            getLocation(
                listOf(query),
            ).mapSuccess { it[0] }
        }

    fun getNearbyJava(query: NearbyItem): CompletableFuture<APIResult<List<Reference>?>> =
        future {
            getNearby(query)
        }
}
