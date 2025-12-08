/*
 * This file is part of breakthelibrary.
 *
 * breakthelibrary is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * breakthelibrary is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with breakthelibrary. If not, see <https://www.gnu.org/licenses/>.
 */
package org.breakthebot.breakthelibrary.objects

import kotlinx.serialization.Serializable

@Serializable
data class ServerInfo(
    val version: String,
    val moonPhase: String,
    val timestamps: Timestamps,
    val status: Status,
    val stats: Stats,
    val voteParty: VoteParty
) {
    @Serializable data class Timestamps(
        val newDayTime: Int,
        val serverTimeOfDay: Int
    )

    @Serializable class Status(
        val hasStorm: Boolean,
        val isThundering: Boolean
    )

    @Serializable data class Stats(
        val time: Long,
        val fullTime: Long,
        val maxPlayers: Int,
        val numOnlinePlayers: Int,
        val numOnlineNomads: Int,
        val numResidents: Int,
        val numNomads: Int,
        val numTowns: Int,
        val numTownBlocks: Int,
        val numNations: Int,
        val numQuarters: Int,
        val numCuboids: Int
    )

    @Serializable data class VoteParty(
        val target: Int,
        val numRemaining: Int
    )

}