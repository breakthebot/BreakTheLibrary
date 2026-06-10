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