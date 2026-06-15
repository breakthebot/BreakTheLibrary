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