package org.breakthebot.breakthelibrary.api.interfaces

import org.breakthebot.breakthelibrary.models.*
import org.breakthebot.breakthelibrary.network.ApiClient.future
import java.util.concurrent.CompletableFuture

interface IServerAPI {
    suspend fun getServerInfo(): ApiResult<ServerInfo>
    suspend fun getPursuits(key: String, type: PursuitType): ApiResult<PursuitResponse>
    suspend fun getMysteryMaster(): ApiResult<List<MysteryMaster>>
    suspend fun getOnlinePlayers(): ApiResult<OnlineReturn>

    fun getMysteryMasterJava() = future { getMysteryMaster() }

    fun getOnlinePlayersJava() = future { getOnlinePlayers() }

    fun getServerInfoJava(): CompletableFuture<ApiResult<ServerInfo>> = future { getServerInfo() }

    fun getPursuitsJava(key: String, type: PursuitType): CompletableFuture<ApiResult<PursuitResponse>> = future { getPursuits(key, type) }
}