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

import org.breakthebot.breakthelibrary.models.Reference
import org.breakthebot.breakthelibrary.models.Town
import org.breakthebot.breakthelibrary.network.Fetch.getRequest
import org.breakthebot.breakthelibrary.network.Fetch.postRequest
import org.breakthebot.breakthelibrary.utils.Endpoints
import java.util.UUID

object TownAPI {

    suspend fun getTown(name: String): Town? = postRequest(Endpoints.TOWNS, listOf(name))
    suspend fun getTown(uuid: UUID): Town? = postRequest(Endpoints.TOWNS, listOf(uuid.toString()))

    suspend fun getTowns(names: List<String>): List<Town>? = postRequest(Endpoints.TOWNS, names)

    suspend fun getAllTowns(): List<Reference>? = getRequest(Endpoints.TOWNS)
}