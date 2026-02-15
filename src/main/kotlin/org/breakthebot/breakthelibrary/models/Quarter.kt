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

@Serializable
data class Quarter(
    val uuid: String,
    val type: String,
    val owner: Reference?,
    val town: Reference,
    val timestamps: Timestamps,
    val status: Status,
    val stats: Stats,
    val colour: List<Int>,
    val trusted: List<Reference>,
    val cuboids: List<Cuboid>
) {
    @Serializable data class Timestamps(
        val registered: Long,
        val claimedAt: Long?
    )

    @Serializable data class Status(
        val isEmbassy: Boolean
    )

    @Serializable data class Stats(
        val price: Float?,
        val volume: Int,
        val numCuboids: Int
    )

    @Serializable @SerialName("cuboids") data class Cuboid(
        val cornerOne: List<Int>,
        val cornerTwo: List<Int>
    )
}


