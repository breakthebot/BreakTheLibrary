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
import kotlinx.serialization.json.Json
import org.breakthebot.breakthelibrary.BreakTheLibrary
import org.breakthebot.breakthelibrary.utils.SerializableUUID

@Serializable
data class Reference(
    val uuid: SerializableUUID,
    val name: String
)

@Serializable
data class Flags(
    var pvp: Boolean = false,
    var explosion: Boolean = false,
    var fire: Boolean = false,
    var mobs: Boolean = false
) {
    override fun toString(): String {
        return BreakTheLibrary.json.encodeToString(this)
    }
}

@Serializable
data class Perms(
    val build: List<Boolean> = listOf(),
    val destroy: List<Boolean> = listOf(),
    val switchPerm: List<Boolean> = listOf(),
    val itemUse: List<Boolean> = listOf(),
    val switch: List<Boolean> = listOf(),
    val flags: Flags = Flags()
) {
    override fun toString(): String {
        return BreakTheLibrary.json.encodeToString(this)
    }
}

@Serializable
data class Spawn(
    val world: String? = null,
    val x: Float = 0F,
    val y: Float = 0F,
    val z: Float = 0F,
    val pitch: Float = 0F,
    val yaw: Float = 0F
) {
    override fun toString(): String  {
        return BreakTheLibrary.json.encodeToString(this)
    }
}

@Serializable
@SerialName("stats")
data class PactStats(
    val createdAt: Long,
    val expiresAt: Long,
    val duration: Int
)

enum class WarpAccess{
    RESIDENT,
    NATION
}

@Serializable
data class WarpLocation(
    val x: Float,
    val z: Float,
    val y: Float
)

enum class PursuitType {
    PLAYER,
    TOWN,
    NATION,
    ALL
}