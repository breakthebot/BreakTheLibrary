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
package org.breakthebot.breakthelibrary.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.JsonElement
import org.breakthebot.breakthelibrary.BreakTheLibrary

@Serializable
data class NearbyItem(
    val target: JsonElement,
    @SerialName("search_type") val searchType: NearbyType,
    @SerialName("target_type") val targetType: NearbyType,
    val radius: Int
) {
    override fun toString(): String {
        return BreakTheLibrary.json.encodeToString(this)
    }
}


enum class NearbyType { TOWN, COORDINATE }

