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

import kotlinx.serialization.Serializable
import org.breakthebot.breakthelibrary.BreakTheLibrary
import kotlin.uuid.Uuid

@Serializable
data class Resident(
    val name: String,
    val uuid: Uuid,
    var title: String? = null,
    var surname: String? = null,
    var formattedName: String? = null,
    var about: String? = null,
    var discord: String? = null,
    var town: Reference? = null,
    var nation: Reference? = null,
    var timestamps: Timestamps = Timestamps(),
    var status: Status = Status(),
    var stats: Stats = Stats(),
    var perms: Perms = Perms(),
    var ranks: Ranks = Ranks(),
    var friends: List<Reference> = emptyList(),
) {
    @Serializable data class Timestamps(
        var registered: Long = 0L,
        var joinedTownAt: Long = 0L,
        var lastOnline: Long = 0L,
    )

    @Serializable
    data class Status(
        var isOnline: Boolean = false,
        var isNPC: Boolean = false,
        var isMayor: Boolean = false,
        var isKing: Boolean = false,
        var hasTown: Boolean = false,
        var hasNation: Boolean = false,
    )

    @Serializable
    data class Stats(
        var balance: Float = 0f,
        var numFriends: Int = 0,
    )

    @Serializable
    data class Ranks(
        var townRanks: List<String> = emptyList(),
        var nationRanks: List<String> = emptyList(),
    )

    override fun toString(): String = BreakTheLibrary.json.encodeToString(this)
}

@Serializable
data class ResidentTemplate(
    val title: Boolean = true,
    val formattedName: Boolean = true,
    val timestamps: Boolean = true,
    val status: Boolean = true,
    val stats: Boolean = true,
    val friends: Boolean = true,
) : Template()
