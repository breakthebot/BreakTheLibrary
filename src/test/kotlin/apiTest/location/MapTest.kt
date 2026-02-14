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
import org.breakthebot.breakthelibrary.models.PlayerMapReturn
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import kotlin.test.assertIs

class MapTest {

    @Test
    fun testMapReturn() {
        runBlocking {
            val players = LocationAPI.getVisiblePlayers()
            assertNotNull(players)
            assertIs<List<PlayerMapReturn>>(players)
        }
    }
}