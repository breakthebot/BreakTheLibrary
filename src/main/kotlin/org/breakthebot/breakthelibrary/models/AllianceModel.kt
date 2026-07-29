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
import kotlin.uuid.Uuid

enum class AllianceType {
    ALLIANCE,
    MEGA_NATION,
}

enum class AllianceFilter {
    SIZE,
    TOWNS,
    RESIDENTS,
    BALANCE,
}

@Serializable
data class AllianceModel(
    val name: String,
    @SerialName("short_name")
    val shortName: String,
    val uuid: Uuid,
    val type: AllianceType,
    val flag: String?,
    val discord: String,
    val leaders: List<Reference>,
    val nations: List<Reference>,
    val color: Int,
    @SerialName("last_updated")
    val lastUpdated: Int,
)

@Serializable
data class AllianceIdentifier(
    val uuid: Uuid,
    val name: String,
    @SerialName("short_name")
    val shortName: String,
)

@Serializable
data class AllianceStats(
    val identifier: AllianceIdentifier,
    val townBlocks: Int,
    val residents: Int,
    val towns: Int,
    val balance: Int,
)

@Serializable
data class AllianceRanking(
    val identifier: AllianceIdentifier,
    val value: Int,
)
