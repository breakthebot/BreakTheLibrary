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

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.serializer
import org.breakthebot.breakthelibrary.api.interfaces.IServerAPI
import org.breakthebot.breakthelibrary.models.APIResult
import org.breakthebot.breakthelibrary.models.MysteryMaster
import org.breakthebot.breakthelibrary.models.OnlineReturn
import org.breakthebot.breakthelibrary.models.PursuitResponse
import org.breakthebot.breakthelibrary.models.PursuitType
import org.breakthebot.breakthelibrary.models.ServerInfo
import org.breakthebot.breakthelibrary.models.StaffList
import org.breakthebot.breakthelibrary.utils.Endpoints
import java.util.UUID
import kotlin.time.TimeSource

open class BaseServerAPI(
    val apiClient: BaseAPIClient,
) : IServerAPI {
    override suspend fun getServerInfo(): APIResult<ServerInfo> = apiClient.getRequest(Endpoints.api_url, serializer<ServerInfo>())

    override suspend fun getPursuits(
        key: String,
        type: PursuitType,
    ): APIResult<PursuitResponse> {
        val query = buildJsonObject {
            put("query", JsonArray(listOf(JsonPrimitive(type.toString()))))
            put("key", key)
        }
        return apiClient.postRequest(Endpoints.PURSUITS, query)
    }

    override suspend fun getMysteryMaster(): APIResult<List<MysteryMaster>> = apiClient.getRequest(Endpoints.MM, serializer<List<MysteryMaster>>())

    override suspend fun getOnlinePlayers(): APIResult<OnlineReturn> = apiClient.getRequest(Endpoints.api_url + "/online", serializer<OnlineReturn>())

    override suspend fun getAPILatency(): Long {
        val start = TimeSource.Monotonic.markNow()
        getServerInfo()
        return start.elapsedNow().inWholeMilliseconds
    }

    override suspend fun getStaff(): Map<String, List<UUID>> = apiClient
        .getRequest(Endpoints.STAFF, serializer<StaffList>())
        .mapSuccess { it.toMap() }
        .getOrElse { emptyMap() }

    override suspend fun getStaffList(): APIResult<StaffList> = apiClient.getRequest(Endpoints.STAFF, serializer<StaffList>())
}

object ServerAPI : BaseServerAPI(APIClient)
