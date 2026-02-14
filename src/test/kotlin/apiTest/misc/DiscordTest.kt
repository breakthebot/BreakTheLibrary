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
import org.breakthebot.breakthelibrary.models.DiscordPayloadDiscord
import org.breakthebot.breakthelibrary.models.DiscordPayloadMinecraft
import org.breakthebot.breakthelibrary.models.DiscordResponse
import org.breakthebot.breakthelibrary.models.Target
import org.breakthebot.breakthelibrary.utils.SerializableUUID
import org.junit.jupiter.api.assertNotNull
import java.util.UUID
import kotlin.test.assertIs

class DiscordTest {

    @Test
    /** Tests the discord endpoint with [DiscordPayloadDiscord] */
    fun testD2D() {
        runBlocking {
            val payload = DiscordPayloadDiscord(
                927581845787402240.toString()
            )
            val resp = DiscordAPI.getDiscord(listOf(payload))?.first()
            assertNotNull(resp)
            assertIs<DiscordResponse>(resp)
        }
    }

    @Test
    /** Tests the discord endpoint with [DiscordPayloadMinecraft] */
    fun testD2M() {
        runBlocking {
            val payload = DiscordPayloadMinecraft(
                SerializableUUID(UUID.fromString("bf65f6f1-3f88-4120-aa34-dbdc6e996b12"))
            )
            val resp = DiscordAPI.getDiscord(listOf(payload))?.first()
            assertNotNull(resp)
            assertIs<DiscordResponse>(resp)

        }
    }
}