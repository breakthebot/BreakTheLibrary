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
@file:Suppress("unused")

package apiTest

import kotlinx.coroutines.runBlocking
import org.breakthebot.breakthelibrary.api.ServerAPI
import org.breakthebot.breakthelibrary.api.TownyAPI
import org.breakthebot.breakthelibrary.models.AllianceFilter
import org.breakthebot.breakthelibrary.models.AllianceModel
import org.breakthebot.breakthelibrary.models.AllianceRanking
import org.breakthebot.breakthelibrary.models.AllianceStats
import org.breakthebot.breakthelibrary.models.Nation
import org.breakthebot.breakthelibrary.models.PursuitType
import org.breakthebot.breakthelibrary.models.Reference
import org.breakthebot.breakthelibrary.models.Resident
import org.breakthebot.breakthelibrary.models.Town
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.uuid.Uuid

class TownyObjectKotlinTest {
    @ParameterizedTest
    @ValueSource(strings = ["charis_k", "Veyronity", "JR1258"])
    fun testResidents(name: String) {
        runBlocking {
            val res = TownyAPI
                .getPlayer(name)
                .getOrNull()
            assertNotNull(res)
            assertIs<Resident>(res)
            assertEquals(res.name, name)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["Paris", "Giza", "Cairo"])
    fun testTowns(name: String) {
        runBlocking {
            val town = TownyAPI
                .getTown(name)
                .onError {
                    println(it)
                }
                .getOrNull()
            assertNotNull(town)
            assertIs<Town>(town)
            assertEquals(town.name, name)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["France", "Egypt", "Germany"])
    fun testNations(name: String) {
        runBlocking {
            val nation = TownyAPI
                .getNation(name)
                .getOrNull()
            assertNotNull(nation)
            assertIs<Nation>(nation)
            assertEquals(nation.name, name)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["charis_k", "Veyronity", "JR1258"])
    fun testD2D(name: String) {
        runBlocking {
            val resp = TownyAPI
                .getPlayerDiscord(name)
                .getOrNull()
            assertNotNull(resp)
            assertIs<String>(resp)
        }
    }

    @Test
    fun testAlliances() {
        runBlocking {
            val resp = TownyAPI
                .getAlliance("African Union")
                .onError { println(it) }
                .getOrNull()
            assertNotNull(resp)
            assertIs<AllianceModel>(resp)
            println(resp)
        }
    }

    @Test
    fun testAllianceStats() {
        runBlocking {
            val resp = TownyAPI
                .getAllianceStats("African Union")
                .onError { println(it) }
                .getOrNull()
            assertNotNull(resp)
            assertIs<AllianceStats>(resp)
        }
    }

    @Test
    fun getTopAlliances() {
        runBlocking {
            val resp = TownyAPI
                .getTopAlliances(AllianceFilter.SIZE)
                .onError { println(it) }
                .onSuccess { println(it) }
                .getOrNull()

            assertNotNull(resp)
            assertIs<List<AllianceRanking>>(resp)
        }
    }

    // @Test
    fun testPursuits() {
        runBlocking {
            val resp = ServerAPI
                .getPursuits(
                    System.getenv("api_key") ?: "",
                    PursuitType.ALL,
                ).mapError {
                    println(it.message)
                    it
                }.getOrNull()
            assertNotNull(resp)
        }
    }

    @Test
    fun testGenericApi() {
        runBlocking {
            val players = TownyAPI.getAllPlayers().getOrNull()
            val towns = TownyAPI.getAllTowns().getOrNull()
            val nations = TownyAPI.getAllNations().getOrNull()
            val alliances = TownyAPI.getAllAlliances().getOrNull()
            assertNotNull(players)
            assertNotNull(towns)
            assertNotNull(nations)
            assertNotNull(alliances)

            assertIs<List<Reference>>(players)
            assertIs<List<Reference>>(towns)
            assertIs<List<Reference>>(nations)
            assertIs<Map<String, Uuid>>(alliances)
        }
    }
}
