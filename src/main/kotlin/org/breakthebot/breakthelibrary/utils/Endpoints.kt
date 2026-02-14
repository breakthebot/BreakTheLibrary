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

object Endpoints {
    const val APIURL = "https://api.earthmc.net/v3/aurora"

    const val TOWNS = "$APIURL/towns"
    const val NATIONS = "$APIURL/nations"
    const val PLAYERS = "$APIURL/players"
    const val LOCATION = "$APIURL/location"
    const val DISCORD = "$APIURL/discord"
    const val NEARBY = "$APIURL/nearby"
    const val MM = "$APIURL/mm"
    const val QUARTERS = "$APIURL/quarters"
    const val STAFF = "https://raw.githubusercontent.com/veyronity/staff/master/staff.json"
    const val MAP = "https://map.earthmc.net/tiles/players.json"
}