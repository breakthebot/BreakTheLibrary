package org.breakthebot.breakthelibrary.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

enum class AllianceType {
    ALLIANCE,
    MEGA_NATION,
}

enum class AllianceFilter {
    SIZE,
    TOWNS,
    RESIDENTS,
    BALANCE,
}

@Serializable
data class AllianceModel(
    val name: String,
    @SerialName("short_name")
    val shortName: String,
    val uuid: Uuid,
    val type: AllianceType,
    val flag: String?,
    val discord: String,
    val leaders: List<String>,
    val nations: List<String>,
    val color: Int,
    @SerialName("last_updated")
    val lastUpdated: Int,
)

@Serializable
data class AllianceIdentifier(
    val name: String,
    val uuid: Uuid,
    @SerialName("short_name")
    val shortName: String,
)

@Serializable
data class AllianceStats(
    val identifier: AllianceIdentifier,
    val townBlocks: Int,
    val residents: Int,
    val towns: Int,
    val balance: Int,
)

@Serializable
data class AllianceRanking(
    val identifier: AllianceIdentifier,
    val value: Int,
)
