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
data class Nation(
    val name: String
) {
    val uuid: SerializableUUID? = null

    var board: String? = null
    var dynmapColour: String? = null
    var dynmapOutline: String? = null
    var wiki: String? = null

    var king: Reference? = null
    var capital: Reference? = null

    var timestamps: Timestamps = Timestamps()
    var status: Status = Status()
    var stats: Stats = Stats()
    var coordinates: Coordinates = Coordinates()

    var residents: List<Reference> = emptyList()
    var towns: List<Reference> = emptyList()
    var allies: List<Reference> = emptyList()
    var enemies: List<Reference> = emptyList()
    var sanctioned: List<Reference> = emptyList()

    var ranks: Ranks = Ranks()

    @Serializable
    data class Timestamps(
        var registered: Long = 0L
    )

    @Serializable
    data class Status(
        var isPublic: Boolean = false,
        var isOpen: Boolean = false,
        var isNeutral: Boolean = false
    )

    @Serializable
    data class Stats(
        var numTownBlocks: Int = 0,
        var numResidents: Int = 0,
        var numTowns: Int = 0,
        var numAllies: Int = 0,
        var numEnemies: Int = 0,
        var balance: Float = 0f
    )

    @Serializable
    data class Coordinates(
        var spawn: Spawn = Spawn()
    )

    @Serializable
    data class Ranks(
        @SerialName("Chancellor")
        var chancellor: List<Reference> = emptyList(),

        @SerialName("Colonist")
        var colonist: List<Reference> = emptyList(),

        @SerialName("diplomat")
        var diplomat: List<Reference> = emptyList()
    )

    override fun toString(): String {
        return BreakTheLibrary.json.encodeToString(this)
    }
}