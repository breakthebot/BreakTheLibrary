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

package org.breakthebot.breakthelibrary.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.UUID

object SerializableUUIDSerializer : KSerializer<SerializableUUID> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("SerializableUUID", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: SerializableUUID) {
        encoder.encodeString(value.value.toString())
    }

    override fun deserialize(decoder: Decoder): SerializableUUID {
        return SerializableUUID(UUID.fromString(decoder.decodeString()))
    }
}

@Serializable(with = SerializableUUIDSerializer::class)
data class SerializableUUID(val value: UUID) {
    fun toUUID(): UUID {
        return value
    }

    override fun toString(): String {
        return value.toString()
    }
}