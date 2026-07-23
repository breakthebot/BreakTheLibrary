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
import org.breakthebot.breakthelibrary.BreakTheLibrary

/**
 * Represents a single query of the nearby endpoint.
 * */
@Serializable
sealed class NearbyItem {
    abstract val searchType: NearbyType

    abstract val targetType: NearbyType
    abstract val radius: Int
    abstract val strict: Boolean

    abstract val target: Any

    @Serializable
    data class NearbyItemCoordinates(
        @SerialName("search_type") override val searchType: NearbyType,
        @SerialName("target_type") override val targetType: NearbyType,
        override val radius: Int,
        override val strict: Boolean = false,
        override val target: List<Int>,
    ) : NearbyItem()

    @Serializable
    data class NearbyItemString(
        @SerialName("search_type") override val searchType: NearbyType,
        @SerialName("target_type") override val targetType: NearbyType,
        override val radius: Int,
        override val strict: Boolean = false,
        override val target: String,
    ) : NearbyItem()

    override fun toString(): String = BreakTheLibrary.json.encodeToString(this)
}

enum class NearbyType { TOWN, COORDINATE, NATION }
