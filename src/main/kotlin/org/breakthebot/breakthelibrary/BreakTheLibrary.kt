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
package org.breakthebot.breakthelibrary

import org.breakthebot.breakthelibrary.utils.ConfigManager
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object BreakTheLibrary {

    const val LOGGING_NAME: String = "BreakTheLibrary"

    val logger: Logger = LoggerFactory.getLogger(LOGGING_NAME)

    init {
        logger.info("Init")
        ConfigManager.loadConfig()
    }

}