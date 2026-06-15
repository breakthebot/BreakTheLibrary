package org.breakthebot.breakthelibrary.api.interfaces

import org.breakthebot.breakthelibrary.models.*
import org.breakthebot.breakthelibrary.network.ApiClient.future
import org.breakthebot.breakthelibrary.utils.SerializableUUID
import java.util.concurrent.CompletableFuture

interface ITownyAPI {

    suspend fun getAllPlayers(): ApiResult<List<Reference>>
    suspend fun getAllTowns(): ApiResult<List<Reference>>
    suspend fun getAllNations(): ApiResult<List<Reference>>

    suspend fun getPlayer(name: String): ApiResult<Resident>

    /**
     * Retrieve the discord user of a player if they are linked.
     * */
    suspend fun getPlayerDiscord(name: String): ApiResult<String>

    suspend fun getPlayers(names: List<String>): List<ApiResult<List<Resident>>>

    suspend fun getTown(name: String): ApiResult<Town>

    suspend fun getTowns(names: List<String>): List<ApiResult<List<Town>>>

    suspend fun getNation(name: String): ApiResult<Nation>

    suspend fun getNations(names: List<String>): List<ApiResult<List<Nation>>>

    suspend fun getStaff(): ApiResult<List<SerializableUUID>>

    // Java API

    fun getAllPlayersJava(): CompletableFuture<ApiResult<List<Reference>>> =
        future { getAllPlayers() }

    fun getAllTownsJava(): CompletableFuture<ApiResult<List<Reference>>> =
        future { getAllTowns() }

    fun getAllNationsJava(): CompletableFuture<ApiResult<List<Reference>>> =
        future { getAllNations() }

    fun getPlayerJava(name: String): CompletableFuture<ApiResult<Resident>> =
        future { getPlayer(name) }

    fun getPlayerDiscordJava(name: String): CompletableFuture<ApiResult<String>> =
        future { getPlayerDiscord(name) }

    fun getPlayersJava(names: List<String>): CompletableFuture<List<ApiResult<List<Resident>>>> =
        future { getPlayers(names) }

    fun getTownJava(name: String): CompletableFuture<ApiResult<Town>> =
        future { getTown(name) }

    fun getTownsJava(names: List<String>): CompletableFuture<List<ApiResult<List<Town>>>> =
        future { getTowns(names) }

    fun getNationJava(name: String): CompletableFuture<ApiResult<Nation>> =
        future { getNation(name) }

    fun getNationsJava(names: List<String>): CompletableFuture<List<ApiResult<List<Nation>>>> =
        future { getNations(names) }

    fun getStaffJava(): CompletableFuture<ApiResult<List<SerializableUUID>>> =
        future { getStaff() }
}