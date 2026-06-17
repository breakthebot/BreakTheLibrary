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
    suspend fun getServerInfo(): APIResult<ServerInfo>
    suspend fun getPursuits(key: String, type: PursuitType): APIResult<PursuitResponse>
    suspend fun getMysteryMaster(): APIResult<List<MysteryMaster>>
    suspend fun getOnlinePlayers(): APIResult<OnlineReturn>
    suspend fun getAPILatency(): Long

    fun getMysteryMasterJava() = future { getMysteryMaster() }

    fun getOnlinePlayersJava() = future { getOnlinePlayers() }

    fun getServerInfoJava(): CompletableFuture<APIResult<ServerInfo>> = future { getServerInfo() }

    fun getPursuitsJava(key: String, type: PursuitType): CompletableFuture<APIResult<PursuitResponse>> = future { getPursuits(key, type) }

    fun getAPILatencyJava() = future { getAPILatency() }
}