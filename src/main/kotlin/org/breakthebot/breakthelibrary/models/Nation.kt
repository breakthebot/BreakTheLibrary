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
import org.breakthebot.breakthelibrary.BreakTheLibrary
import org.breakthebot.breakthelibrary.utils.SerializableUUID


@Serializable
data class Nation(
    val name: String,
    val uuid: SerializableUUID,

    val board: String? = null,
    val dynmapColour: String? = null,
    val dynmapOutline: String? = null,
    val wiki: String? = null,
    val discord: String? = null,

    val king: Reference,
    val capital: Reference,

    val timestamps: Timestamps = Timestamps(),
    val status: Status = Status(),
    val stats: Stats = Stats(),
    val coordinates: Coordinates = Coordinates(),

    val residents: List<Reference> = emptyList(),
    val towns: List<Reference> = emptyList(),
    val allies: List<Reference> = emptyList(),
    val enemies: List<Reference> = emptyList(),
    val sanctioned: List<Reference> = emptyList(),

    val ranks: Ranks = Ranks(),

    val embargoes: Embargoes = Embargoes(),

    val pacts: Pacts = Pacts()
) {
    @Serializable
    data class Timestamps(
        val registered: Long = 0L
    )

    @Serializable
    data class Status(
        var isPublic: Boolean = false,
        var isOpen: Boolean = false,
        var isNeutral: Boolean = false
    )

    @Serializable
    data class Stats(
        val numTownBlocks: Int = 0,
        val numResidents: Int = 0,
        val numTowns: Int = 0,
        val numAllies: Int = 0,
        val numEnemies: Int = 0,
        val nationBonus: Int = 0,
        val balance: Float = 0f
    )

    @Serializable
    data class Coordinates(
        var spawn: Spawn = Spawn()
    )

    @Serializable
    data class Ranks(
        @SerialName("Chancellor")
        val chancellor: List<Reference> = emptyList(),

        @SerialName("Colonist")
        val colonist: List<Reference> = emptyList(),

        @SerialName("diplomat")
        val diplomat: List<Reference> = emptyList(),

        @SerialName("Treasurer")
        val treasurer: List<Reference> = emptyList()
    )

    @Serializable
    data class PactData(
        val sender: String,
        val receiver: String,
        val status: String,
        val stats: PactStats
    )

    @Serializable
    data class Pacts(
        val active: List<PactData> = listOf(),
        val pending: List<PactData> = listOf()
    )

    @Serializable
    data class Embargoes(
        val own: List<Reference> = listOf(),
        val against: List<Reference> = listOf()
    )

    override fun toString(): String {
        return BreakTheLibrary.json.encodeToString(this)
    }
}