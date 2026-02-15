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
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.breakthebot.breakthelibrary.BreakTheLibrary.logger
import java.io.File

@Serializable
data class Urls(
    val apiUrl: String = "https://api.earthmc.net/",
    val mapUrl: String = "https://map.earthmc.net/tiles/players.json",
    val staffUrl: String = "https://raw.githubusercontent.com/veyronity/staff/master/staff.json"
)

@Serializable
data class Config(
    val urls: Urls = Urls(),
)

object ConfigManager {
    var config = Config()
    val configFile = File(System.getProperty("user.dir"), "config")

    fun loadConfig() {
        if (!configFile.exists()) { saveConfig(null) }
        val fileContent = configFile.readText()

        if (fileContent.isEmpty() || fileContent.length <= 2) {
            saveConfig(null)
        }

        try {
            config = Json.decodeFromString<Config>(fileContent)
        } catch (e: Exception) {
            logger.error("Unable to parse config file regenerating, ${e.message}")
            saveConfig(null)
        }
    }

    fun saveConfig(data: Config?) {
        val data = data ?: Config()
        try {
            val encoded= Json.encodeToString<Config>(data)
            configFile.writeText(encoded)
        } catch (e: Exception) {
            logger.error("Unable to write new config, ${e.message}")
        }
    }
}