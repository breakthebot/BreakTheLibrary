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

import kotlinx.serialization.Serializable

@Serializable
data class Config(
    val apiUrl: String = "https://api.earthmc.net/v4",
    val mapUrl: String = "https://map.earthmc.net/",
    val staffUrl: String = "https://raw.githubusercontent.com/veyronity/staff/master/staff.json",

    val batchSize: Int = 100
)

object ConfigHandler {
    var cfg = Config()

    fun setup(
        conf: Config
    ) { this.cfg = conf }
}