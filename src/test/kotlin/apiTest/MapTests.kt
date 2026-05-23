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
package apiTest

import kotlinx.coroutines.runBlocking
import org.breakthebot.breakthelibrary.api.MapApi
import org.breakthebot.breakthelibrary.models.NearbyItem
import org.breakthebot.breakthelibrary.models.NearbyType
import org.breakthebot.breakthelibrary.models.PlayerMapReturn
import org.breakthebot.breakthelibrary.models.Reference
import org.breakthebot.breakthelibrary.models.getOrNull
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import kotlin.test.assertIs

class MapTests {

    @Test
    fun testNearbyApi() {
        runBlocking {
            val nearby = MapApi.getNearby(
                listOf(
                    NearbyItem(
                        NearbyType.TOWN,
                        "Cairo",
                        NearbyType.TOWN,
                        500
                    )
                )
            ).getOrNull()
            assertNotNull(nearby)
            assertIs<List<Reference>>(nearby)
        }
    }

    @Test
    fun testLocation() {
        val coords = Pair(11125.0, -3371.0)
        runBlocking {
            val loc = MapApi.getLocation(listOf(coords))
                .getOrNull()
                ?.first()

            assertNotNull(loc)
            Assertions.assertEquals(loc.town?.name, "Giza")
            Assertions.assertEquals(loc.nation?.name, "Egypt")
        }
    }

    @Test
    fun testMapReturn() {
        runBlocking {
            val players = MapApi.getVisiblePlayers()
            assertNotNull(players)
            assertIs<List<PlayerMapReturn>>(players)
        }
    }
}