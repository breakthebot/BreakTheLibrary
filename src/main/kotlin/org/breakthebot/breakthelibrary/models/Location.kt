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
import org.breakthebot.breakthelibrary.utils.SerializableUUID

@Serializable
data class Location (
    val name: String? = null,
    val location: Coordinates? = null,
    val isWilderness: Boolean? = null,
    val town: Reference? = null,
    val nation: Reference? = null
) {
    @Serializable data class Coordinates(
        val x: Double? = null,
        val z: Double? = null
    )
}

@Serializable
data class MapReturn(
    val max: Int,
    val players: List<PlayerMapReturn>
)
@Serializable
data class PlayerMapReturn(
    val world: String,
    val name: String,
    val x: Double,
    val y: Double,
    val z: Double,
    @SerialName("display_name")
    val displayName: String,
    val uuid: String,
    val yaw: Int
)