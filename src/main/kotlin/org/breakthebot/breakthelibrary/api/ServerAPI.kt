package org.breakthebot.breakthelibrary.api

import org.breakthebot.breakthelibrary.models.ApiResult
import org.breakthebot.breakthelibrary.models.ServerInfo
import org.breakthebot.breakthelibrary.network.ApiClient
import org.breakthebot.breakthelibrary.network.ApiClient.future
import org.breakthebot.breakthelibrary.utils.Endpoints
import java.util.concurrent.CompletableFuture

object ServerAPI {
    suspend fun getServerInfo(): ApiResult<ServerInfo> = ApiClient.getRequest(Endpoints.APIURL)

    fun getServerInfoJava(): CompletableFuture<ApiResult<ServerInfo>> = future { getServerInfo() }
}