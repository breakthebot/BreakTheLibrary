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

@Serializable
data class PlayerLocationInfo(
    val username: String,
    var x: Double,
    var z: Double,
    var isWilderness: Boolean,
    var townName: String?,
    var found: Boolean
) {

    init {
        this.found = found
    }

    override fun toString(): String {
        return if (!found) {
            "$username is either offline or not showing up on the map."
        } else if (isWilderness) {
            "$username at x: $x, z: $z is in wilderness."
        } else {
            "$username at x: $x, z: $z is in town: $townName."
        }
    }
}