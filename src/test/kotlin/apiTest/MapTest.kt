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
import org.breakthebot.breakthelibrary.api.MapAPI
import org.breakthebot.breakthelibrary.models.NearbyItem
import org.breakthebot.breakthelibrary.models.NearbyType
import org.breakthebot.breakthelibrary.models.PlayerMapReturn
import org.breakthebot.breakthelibrary.models.Reference
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import kotlin.test.assertIs

class MapTest {
    @Test
    fun testNearbyApiTown() {
        runBlocking {
            val query = NearbyItem.NearbyItemString(
                target = "Cairo",
                searchType = NearbyType.TOWN,
                targetType = NearbyType.TOWN,
                radius = 500,
            )
            val nearby = MapAPI
                .getNearby(
                    query,
                ).getOrNull()
            assertNotNull(nearby)
            assertIs<List<Reference>>(nearby)
        }
    }

    @Test
    fun testNearbyApiCoords() {
        runBlocking {
            val query = NearbyItem.NearbyItemCoordinates(
                target = listOf(11346, 11346),
                searchType = NearbyType.TOWN,
                targetType = NearbyType.COORDINATE,
                radius = 500,
            )
            val nearby = MapAPI
                .getNearby(
                    query,
                ).getOrNull()
            assertNotNull(nearby)
            assertIs<List<Reference>>(nearby)
        }
    }

    @Test
    fun testNearbyApiNation() {
        runBlocking {
            val query = NearbyItem.NearbyItemString(
                target = "Giza",
                searchType = NearbyType.NATION,
                targetType = NearbyType.TOWN,
                radius = 500,
            )
            val nearby = MapAPI
                .getNearby(
                    query,
                ).getOrNull()
            assertNotNull(nearby)
            assertIs<List<Reference>>(nearby)
        }
    }

    @Test
    fun testLocation() {
        val coords = Pair(11125.0, -3371.0)
        runBlocking {
            val loc = MapAPI
                .getLocation(listOf(coords))
                .getOrNull()
                ?.first()

            println(loc.toString())
            assertNotNull(loc)
            Assertions.assertEquals("Giza", loc.town?.name)
            Assertions.assertEquals("Egypt", loc.nation?.name)
        }
    }

    @Test
    fun testMapReturn() {
        runBlocking {
            val players = MapAPI.getVisiblePlayers()
            assertNotNull(players)
            assertIs<List<PlayerMapReturn>>(players)
        }
    }
}
