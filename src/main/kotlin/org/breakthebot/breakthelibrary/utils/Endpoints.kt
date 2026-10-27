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

    val alliance_url: String
        get() = ConfigHandler.cfg.allianceApiUrl

    val players: String
        get() = "$api_url/players"

    val towns: String
        get() = "$api_url/towns"

    val nations: String
        get() = "$api_url/nations"

    val location: String
        get() = "$api_url/location"

    val nearby: String
        get() = "$api_url/nearby"

    val pursuits: String
        get() = "$api_url/pursuits"

    val mm: String
        get() = "$api_url/mm"

    val staff: String
        get() = ConfigHandler.cfg.staffUrl

    val online_players: String
        get() = "$api_url/online"

    val map: String
        get() = ConfigHandler.cfg.mapUrl

    val map_api: String
        get() = "$map/tiles/players.json"

    val alliances_api: String
        get() = alliance_url

    val alliance_stats: String
        get() = "$alliance_url/stats"

    val alliances_top: String
        get() = "$alliance_url/top"

    val alliance_nation_membership: String
        get() = "$alliance_url/nation-membership"
}
