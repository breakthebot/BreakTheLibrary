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

import org.breakthebot.breakthelibrary.models.Nation
import org.breakthebot.breakthelibrary.models.Resident
import org.breakthebot.breakthelibrary.models.Town
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlinx.coroutines.runBlocking
import org.breakthebot.breakthelibrary.api.TownyAPI
import org.breakthebot.breakthelibrary.models.ApiResult
import org.breakthebot.breakthelibrary.models.Reference
import org.breakthebot.breakthelibrary.models.getOrNull
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs


class TownyObjectTests {

    @ParameterizedTest
    @ValueSource(strings = ["Paris", "Giza", "Cairo"])
    fun testTowns(name: String) {
        runBlocking{
            val town = when ( val town = TownyAPI.getTown(name) ) {
                is ApiResult.Success<Town> -> {
                    town.data
                }
                is ApiResult.Error -> null
            }
            assertNotNull(town)
            assertIs<Town>(town)
            assertEquals(town.name, name)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["charis_k", "Veyronity", "JR1258"])
    fun testResidents(name: String) {
        runBlocking{
            val res = when (val res = TownyAPI.getPlayer(name)) {
                is ApiResult.Success<Resident> -> {
                    res.data
                }
                is ApiResult.Error -> null
            }
            assertNotNull(res)
            assertIs<Resident>(res)
            assertEquals(res.name, name)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["France", "Egypt", "Germany"])
    fun testNations(name: String) {
        runBlocking{
            val nation = when( val nation = TownyAPI.getNation(name) ) {
                is ApiResult.Success<Nation> -> {
                    nation.data
                }
                is ApiResult.Error -> null
            }
            assertNotNull(nation)
            assertIs<Nation>(nation)
            assertEquals(nation.name, name)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["charis_k", "Veyronity", "JR1258"])
    fun testD2D(name: String) {
        runBlocking {
            val resp = TownyAPI.getPlayerDiscord(name).getOrNull()
            assertNotNull(resp)
            assertIs<String>(resp)
        }
    }

    @Test
    fun testGenericApi() {
        runBlocking {
            val players = TownyAPI.getAllPlayers().getOrNull()
            val towns = TownyAPI.getAllTowns().getOrNull()
            val nations = TownyAPI.getAllNations().getOrNull()
            assertNotNull(players)
            assertNotNull(towns)
            assertNotNull(nations)

            assertIs<List<Reference>>(players)
            assertIs<List<Reference>>(towns)
            assertIs<List<Reference>>(nations)
        }
    }
}