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

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid

@Serializable
data class StaffList(
    @Contextual
    val owner: List<Uuid>,
    @Contextual
    val admin: List<Uuid>,
    @Contextual
    val developer: List<Uuid>,
    @Contextual
    val moderator: List<Uuid>,
    @Contextual
    val helper: List<Uuid>,
) {
    fun allStaff(): List<Uuid> = (owner + admin + moderator + helper + developer).distinct()

    fun toList(): List<UUID> = allStaff().map { it.toJavaUuid() }

    fun toMap(): Map<String, List<UUID>> {
        val map = mutableMapOf<String, List<UUID>>()
        map["owner"] = owner.map { it.toJavaUuid() }
        map["admin"] = admin.map { it.toJavaUuid() }
        map["developer"] = developer.map { it.toJavaUuid() }
        map["moderator"] = moderator.map { it.toJavaUuid() }
        map["helper"] = helper.map { it.toJavaUuid() }
        return map
    }
}
