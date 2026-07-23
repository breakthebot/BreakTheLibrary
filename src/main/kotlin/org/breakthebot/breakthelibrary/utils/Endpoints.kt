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

/**
 * Helper utility that carries all the api endpoints.
 * */
object Endpoints {
    val api_url: String
        get() = ConfigHandler.cfg.apiUrl

    val PLAYERS: String
        get() = "$api_url/players"

    val TOWNS: String
        get() = "$api_url/towns"

    val NATIONS: String
        get() = "$api_url/nations"

    val LOCATION: String
        get() = "$api_url/location"

    val NEARBY: String
        get() = "$api_url/nearby"

    val PURSUITS: String
        get() = "$api_url/pursuits"

    val MM: String
        get() = "$api_url/mm"

    val STAFF: String
        get() = ConfigHandler.cfg.staffUrl

    val MAP: String
        get() = ConfigHandler.cfg.mapUrl

    val MAP_API: String
        get() = "$MAP/tiles/players.json"
}
