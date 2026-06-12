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
    val APIURL = ConfigHandler.cfg.apiUrl

    val TOWNS = "$APIURL/towns"
    val NATIONS = "$APIURL/nations"
    val PLAYERS = "$APIURL/players"
    val LOCATION = "$APIURL/location"
    val NEARBY = "$APIURL/nearby"
    val PURSUITS = "$APIURL/pursuits"
    val MM = "${APIURL}/mm"
    val STAFF = ConfigHandler.cfg.staffUrl
    val MAP = ConfigHandler.cfg.mapUrl + "tiles/players.json"
}