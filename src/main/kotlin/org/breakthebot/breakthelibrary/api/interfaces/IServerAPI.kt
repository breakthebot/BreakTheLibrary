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

import org.breakthebot.breakthelibrary.api.APIClient.future
import org.breakthebot.breakthelibrary.models.APIResult
import org.breakthebot.breakthelibrary.models.MysteryMaster
import org.breakthebot.breakthelibrary.models.OnlineReturn
import org.breakthebot.breakthelibrary.models.PursuitResponse
import org.breakthebot.breakthelibrary.models.PursuitType
import org.breakthebot.breakthelibrary.models.ServerInfo
import org.breakthebot.breakthelibrary.models.StaffList
import java.util.UUID

interface IServerAPI {
    suspend fun getServerInfo(): APIResult<ServerInfo>

    suspend fun getPursuits(
        key: String,
        type: PursuitType,
    ): APIResult<PursuitResponse>

    suspend fun getMysteryMaster(): APIResult<List<MysteryMaster>>

    suspend fun getOnlinePlayers(): APIResult<OnlineReturn>

    suspend fun getAPILatency(): Long

    suspend fun getStaffList(): APIResult<StaffList>

    suspend fun getStaff(): Map<String, List<UUID>>

    fun getMysteryMasterJava() = future { getMysteryMaster() }

    fun getOnlinePlayersJava() = future { getOnlinePlayers() }

    fun getServerInfoJava() = future { getServerInfo() }

    fun getPursuitsJava(
        key: String,
        type: PursuitType,
    ) = future { getPursuits(key, type) }

    fun getAPILatencyJava() = future { getAPILatency() }

    fun getStaffListJava() = future { getStaffList() }

    fun getStaffJava() = future { getStaff() }
}
