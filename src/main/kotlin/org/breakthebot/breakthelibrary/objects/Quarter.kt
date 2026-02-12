package org.breakthebot.breakthelibrary.objects

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
    val trusted: List<String>,
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
        val price: Int?,
        val volume: Int,
        val numCuboids: Int
    )

    @Serializable data class Cuboid(
        val pos1: List<Int>,
        val pos2: List<Int>
    )
}


