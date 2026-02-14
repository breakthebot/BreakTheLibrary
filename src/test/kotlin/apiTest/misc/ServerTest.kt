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
package apiTest.misc

import kotlinx.coroutines.runBlocking
import org.breakthebot.breakthelibrary.api.ServerAPI
import org.breakthebot.breakthelibrary.models.ServerInfo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import kotlin.test.assertIs

class ServerTest {
    
    @Test
    fun testServer() {
        runBlocking {
            val info = ServerAPI.getServer()
            assertNotNull(info)
            assertIs<ServerInfo>(info)
        }
    }
}