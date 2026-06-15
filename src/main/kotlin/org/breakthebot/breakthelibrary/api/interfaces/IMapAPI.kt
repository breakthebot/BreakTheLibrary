package org.breakthebot.breakthelibrary.api.interfaces

import org.breakthebot.breakthelibrary.models.*
import org.breakthebot.breakthelibrary.network.ApiClient.future
import java.util.concurrent.CompletableFuture

interface IMapAPI {

    suspend fun getVisiblePlayers(): List<PlayerMapReturn>?

    suspend fun getLocation(query: List<Pair<Double, Double>>): ApiResult<List<Location>>

    suspend fun getNearby(query: NearbyItem): ApiResult<List<Reference>?>

    fun getVisiblePlayersJava(): CompletableFuture<List<PlayerMapReturn>?>

    fun getLocationJava(query: List<Pair<Double, Double>>): CompletableFuture<ApiResult<List<Location>>> = future { getLocation(query) }

    fun getLocationJava(query: Pair<Double, Double>): CompletableFuture<ApiResult<Location>> = future { getLocation(
        listOf(query)
    ).mapSuccess { it[0] } }

    fun getNearbyJava(query: NearbyItem): CompletableFuture<ApiResult<List<Reference>?>> = future {
        getNearby(query)
    }
}