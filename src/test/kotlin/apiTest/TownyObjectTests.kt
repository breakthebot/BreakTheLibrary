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

import kotlinx.coroutines.launch
import org.breakthebot.breakthelibrary.api.NationAPI
import org.breakthebot.breakthelibrary.api.PlayerAPI
import org.breakthebot.breakthelibrary.api.TownAPI
import org.breakthebot.breakthelibrary.models.Nation
import org.breakthebot.breakthelibrary.models.Resident
import org.breakthebot.breakthelibrary.models.Town
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import TestScope
import kotlinx.coroutines.runBlocking
import kotlin.test.assertEquals
import kotlin.test.assertIs


class TownyObjectTests {
    @ParameterizedTest
    @ValueSource(strings = ["Paris", "Konoha", "Lost_Coast"])
    fun testTowns(name: String) {
        runBlocking{
            val town = TownAPI.getTown(name)
            assertNotNull(town)
            assertIs<Town>(town)
            assertEquals(town.name, name)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["charis_k", "Veyronity", "JR1258"])
    fun testResidents(name: String) {
        runBlocking{
            val res = PlayerAPI.getPlayer(name)
            assertNotNull(res)
            assertIs<Resident>(res)
            assertEquals(res.name, name)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["France", "Mali", "Netherlands"])
    fun testNations(name: String) {
        runBlocking{
            val nation = NationAPI.getNation(name)
            assertNotNull(nation)
            assertIs<Nation>(nation)
            assertEquals(nation.name, name)
        }
    }

}