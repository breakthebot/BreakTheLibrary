/*
 * This file is part of breakthelibrary.
 *
 * breakthelibrary is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * breakthelibrary is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with breakthelibrary. If not, see <https://www.gnu.org/licenses/>.
 */

package org.breakthebot.breakthelibrary.objects

import org.breakthebot.breakthelibrary.BreakTheLibrary
import java.util.logging.Logger


enum class ErrorEnum {
    NotFound,
    InternalApiError,
    UnableToConnectAPI,
    Unknown
}

data class ErrorObject(
    val error: ErrorEnum,
    val message: String
) {
    fun log() {
        val message: String = when(error) {
            ErrorEnum.NotFound -> "Unable to find $message"
            ErrorEnum.InternalApiError -> "Internal api error $message"
            ErrorEnum.UnableToConnectAPI -> "Unable to reach the connect to the emc api, $message"
            ErrorEnum.Unknown -> message
        }
        Logger.getLogger(BreakTheLibrary.LOGGING_NAME).severe { message }
    }
}