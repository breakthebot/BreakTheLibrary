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

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import org.breakthebot.breakthelibrary.models.DiscordPayload
import org.breakthebot.breakthelibrary.models.DiscordResponse
import org.breakthebot.breakthelibrary.network.Fetch.postRequest
import org.breakthebot.breakthelibrary.utils.Endpoints

class DiscordAPI {

    suspend fun getDiscord(query: List<DiscordPayload>): List<DiscordResponse>? {
        val body = buildJsonObject {
            put("query", JsonArray(query.map { Json.encodeToJsonElement(it).jsonObject }))
        }
        return postRequest<List<DiscordResponse>?>(Endpoints.DISCORD, body)
    }
}