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
package org.breakthebot.breakthelibrary.routines

import org.breakthebot.breakthelibrary.objects.ErrorObject
import java.util.UUID
import org.breakthebot.breakthelibrary.objects.Result

/**
 * Basic representation of a schedule.
 * @param T Return type.
 * */
interface Schedule<T> {
    val name: String
    val id: UUID
    val returnType: Result<T?, ErrorObject?>
}




