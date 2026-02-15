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
package apiTest.location

import kotlinx.coroutines.runBlocking
import org.breakthebot.breakthelibrary.api.LocationAPI
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull


class LocationTest {

    @Test
    fun testLocation() {
        val coords = Pair(397.0, -9145.0)
        runBlocking {
            val loc = LocationAPI.getLocation(listOf(coords))?.first()
            assertNotNull(loc)
            assertEquals(loc.town?.name, "Paris")
            assertEquals(loc.nation?.name, "France")
        }
    }
}