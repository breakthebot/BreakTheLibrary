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
data class Town(
    val name: String = "",
    val uuid: SerializableUUID,
    val board: String? = null,
    val founder: String? = null,
    val wiki: String? = null,
    val discord: String? = null,
    val mayor: Reference,
    val nation: Reference? = null,
    val timestamps: Timestamps = Timestamps(),
    val status: Status = Status(),
    val stats: Stats = Stats(),
    val perms: Perms = Perms(),
    val coordinates: Coordinates = Coordinates(),
    val residents: List<Reference> = emptyList(),
    val trusted: List<Reference> = emptyList(),
    val outlaws: List<Reference> = emptyList(),
    val quarters: List<Reference> = emptyList(),
    val ranks: Ranks = Ranks(),
    val warps: List<Warp> = listOf()
) {

    @Serializable
    data class Timestamps(
        val registered: Long = 0L,
        val joinedNationAt: Long? = 0L,
        val ruinedAt: Long? = 0L
    )

    @Serializable
    data class Status(
        val isPublic: Boolean = false,
        val isOpen: Boolean = false,
        val isNeutral: Boolean = false,
        val isCapital: Boolean = false,
        val isOverClaimed: Boolean = false,
        val isRuined: Boolean = false,
        val isForSale: Boolean = false,
        val hasNation: Boolean = false,
        val hasOverclaimShield: Boolean = false,
        val canOutsidersSpawn: Boolean = false,
        val canPassiveMobSpawn: Boolean = false,
        val hasSnowAccumulation: Boolean = false,
        val hasFriendlyFire: Boolean = false
    )

    @Serializable
    data class Stats(
        val numTownBlocks: Int = 0,
        val maxTownBlocks: Int = 0,
        val numResidents: Int = 0,
        val numTrusted: Int = 0,
        val numOutlaws: Int = 0,
        val bonusBlocks: Int = 0,
        val balance: Float = 0f,
        val forSalePrice: Float? = 0f
    )

    @Serializable
    data class Coordinates(
        val spawn: Spawn = Spawn(),
        val homeBlock: List<Int> = emptyList(),
        val townBlocks: List<List<Int>> = emptyList()
    )

    @Serializable
    data class Ranks(
        @SerialName("Councilor")
        val councillor: List<Reference> = emptyList(),

        @SerialName("Builder")
        val builder: List<Reference> = emptyList(),

        @SerialName("Recruiter")
        val recruiter: List<Reference> = emptyList(),

        @SerialName("Police")
        val police: List<Reference> = emptyList(),

        @SerialName("Tax-Exempt")
        val taxExempt: List<Reference> = emptyList(),

        @SerialName("Treasurer")
        val treasurer: List<Reference> = emptyList(),

        @SerialName("Realtor")
        val realtor: List<Reference> = emptyList(),

        @SerialName("Settler")
        val settler: List<Reference> = emptyList()
    )

    @Serializable
    data class Warp(
        val name: String,
        val uuid: SerializableUUID,
        val createdAt: Long,
        val createdBy: String,
        val access: WarpAccess,
        val location: WarpLocation
    )


    override fun toString(): String {
        return BreakTheLibrary.json.encodeToString(this)
    }
}