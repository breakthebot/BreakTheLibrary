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

import org.breakthebot.breakthelibrary.models.*
import org.breakthebot.breakthelibrary.network.ApiClient.future
import org.breakthebot.breakthelibrary.utils.SerializableUUID
import java.util.concurrent.CompletableFuture

interface ITownyAPI {

    suspend fun getAllPlayers(): APIResult<List<Reference>>
    suspend fun getAllTowns(): APIResult<List<Reference>>
    suspend fun getAllNations(): APIResult<List<Reference>>

    /**
     * Retrieve the discord user of a player if they are linked.
     * */
    suspend fun getPlayer(name: String): APIResult<Resident>
    suspend fun getPlayerDiscord(name: String): APIResult<String>
    suspend fun getPlayers(names: List<String>): List<APIResult<List<Resident>>>
    suspend fun getTown(name: String): APIResult<Town>
    suspend fun getTowns(names: List<String>): List<APIResult<List<Town>>>
    suspend fun getNation(name: String): APIResult<Nation>
    suspend fun getNations(names: List<String>): List<APIResult<List<Nation>>>

    // Java API

    fun getAllPlayersJava(): CompletableFuture<APIResult<List<Reference>>> =
        future { getAllPlayers() }

    fun getAllTownsJava(): CompletableFuture<APIResult<List<Reference>>> =
        future { getAllTowns() }

    fun getAllNationsJava(): CompletableFuture<APIResult<List<Reference>>> =
        future { getAllNations() }

    fun getPlayerJava(name: String): CompletableFuture<APIResult<Resident>> =
        future { getPlayer(name) }

    fun getPlayerDiscordJava(name: String): CompletableFuture<APIResult<String>> =
        future { getPlayerDiscord(name) }

    fun getPlayersJava(names: List<String>): CompletableFuture<List<APIResult<List<Resident>>>> =
        future { getPlayers(names) }

    fun getTownJava(name: String): CompletableFuture<APIResult<Town>> =
        future { getTown(name) }

    fun getTownsJava(names: List<String>): CompletableFuture<List<APIResult<List<Town>>>> =
        future { getTowns(names) }

    fun getNationJava(name: String): CompletableFuture<APIResult<Nation>> =
        future { getNation(name) }

    fun getNationsJava(names: List<String>): CompletableFuture<List<APIResult<List<Nation>>>> =
        future { getNations(names) }
}
