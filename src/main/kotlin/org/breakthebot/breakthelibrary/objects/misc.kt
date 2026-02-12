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
package org.breakthebot.breakthelibrary.objects

import kotlinx.serialization.Serializable
import org.breakthebot.breakthelibrary.utils.SerializableUUID

@Serializable
data class Reference(
    val uuid: SerializableUUID?,
    val name: String?
)

@Serializable
data class Flags(
    var pvp: Boolean? = null,
    var explosion: Boolean? = null,
    var fire: Boolean? = null,
    var mobs: Boolean? = null
)

@Serializable
data class Spawn(
    val world: String? = null,
    val x: Float? = null,
    val y: Float? = null,
    val z: Float? = null,
    val pitch: Float? = null,
    val yaw: Float? = null
)

@Serializable
data class Perms(
    val build: List<Boolean>? = null,
    val destroy: List<Boolean>? = null,
    val switchPerm: List<Boolean>? = null,
    val itemUse: List<Boolean>? = null,
    val switch: List<Boolean>? = null,
    val flags: Flags? = null
)