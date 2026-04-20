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
import org.breakthebot.breakthelibrary.api.DiscordAPI
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.UUID
import kotlin.test.assertIs

class DiscordTest {

    @ParameterizedTest
    @ValueSource(strings = ["charis_k", "Veyronity"])
    fun testD2D(name: String) {
        runBlocking {
            val resp = DiscordAPI.getDiscord(listOf(name))?.first()
            assertNotNull(resp)
            assertIs<String>(resp)
        }
    }
}