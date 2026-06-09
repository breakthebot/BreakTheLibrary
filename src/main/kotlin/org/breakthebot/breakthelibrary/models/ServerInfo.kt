package org.breakthebot.breakthelibrary.models

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

    @Serializable data class Status(
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

