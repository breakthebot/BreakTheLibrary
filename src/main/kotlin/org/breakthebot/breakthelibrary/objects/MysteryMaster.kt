package org.breakthebot.breakthelibrary.objects

import kotlinx.serialization.Serializable
import org.breakthebot.breakthelibrary.utils.SerializableUUID

@Serializable
data class MysteryMaster(
    val name: String?,
    val uuid: SerializableUUID?,
    val change: String?
)