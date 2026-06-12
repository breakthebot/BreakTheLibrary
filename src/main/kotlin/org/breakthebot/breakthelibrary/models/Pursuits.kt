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
data class PursuitTop(
    val uuid: SerializableUUID,
    val score: Float = 0F
)

@Serializable
data class PlayerPursuits(
    val name: String,
    val isActive: Boolean,
    val top: Map<String, PursuitTop>
)

@Serializable
data class TownPursuits(
    val name: String,
    val isActive: Boolean,
    val top: Map<String, PursuitTop>
)

@Serializable
data class NationPursuits(
    val name: String,
    val isActive: Boolean,
    val top: Map<String, PursuitTop>
)

@Serializable
data class PursuitResponse(
    @SerialName("PLAYER") val player: PlayerPursuits?,
    @SerialName("TOWN") val town: TownPursuits?,
    @SerialName("NATION") val nation: NationPursuits?
)