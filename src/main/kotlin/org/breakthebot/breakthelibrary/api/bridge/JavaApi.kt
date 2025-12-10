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
package org.breakthebot.breakthelibrary.api.bridge

import org.breakthebot.breakthelibrary.api.Api
import org.breakthebot.breakthelibrary.objects.DiscordPayload
import org.breakthebot.breakthelibrary.objects.DiscordResponse
import org.breakthebot.breakthelibrary.objects.ErrorObject
import org.breakthebot.breakthelibrary.objects.Location
import org.breakthebot.breakthelibrary.objects.MapReturn
import org.breakthebot.breakthelibrary.objects.MysteryMaster
import org.breakthebot.breakthelibrary.objects.Nation
import org.breakthebot.breakthelibrary.objects.NearbyItem
import org.breakthebot.breakthelibrary.objects.Reference
import org.breakthebot.breakthelibrary.objects.Resident
import org.breakthebot.breakthelibrary.objects.Result
import org.breakthebot.breakthelibrary.objects.ServerInfo
import org.breakthebot.breakthelibrary.objects.Town
import org.breakthebot.breakthelibrary.objects.resultFuture
import org.breakthebot.breakthelibrary.utils.SerializableUUID
import java.util.UUID
import java.util.concurrent.CompletableFuture

object JavaApi : IApiJava {
    private val api = Api

    override fun getTowns(): CompletableFuture<Result<List<Reference>?, ErrorObject?>> = resultFuture { api.getTowns() }

    override fun getTowns(names: List<String>): CompletableFuture<Result<List<Town>?, ErrorObject?>> = resultFuture { api.getTowns(names) }

    override fun getTowns(names: Array<UUID>): CompletableFuture<Result<List<Town>?, ErrorObject?>> = resultFuture { api.getTowns(names) }

    override fun getTown(name: String): CompletableFuture<Result<Town?, ErrorObject?>> = resultFuture { api.getTown(name) }

    override fun getTown(name: UUID): CompletableFuture<Result<Town?, ErrorObject?>> = resultFuture { api.getTown(name) }

    override fun getNations(): CompletableFuture<Result<List<Reference>?, ErrorObject?>> = resultFuture { api.getNations() }

    override fun getNations(names: List<String>): CompletableFuture<Result<List<Nation>?, ErrorObject?>> = resultFuture { api.getNations(names) }

    override fun getNations(names: Array<UUID>): CompletableFuture<Result<List<Nation>?, ErrorObject?>> = resultFuture { api.getNations(names) }

    override fun getNation(name: String): CompletableFuture<Result<Nation?, ErrorObject?>> = resultFuture { api.getNation(name) }

    override fun getNation(name: UUID): CompletableFuture<Result<Nation?, ErrorObject?>> = resultFuture { api.getNation(name) }

    override fun getPlayers(): CompletableFuture<Result<List<Reference>?, ErrorObject?>> = resultFuture { api.getPlayers() }

    override fun getPlayers(names: List<String>): CompletableFuture<Result<List<Resident>?, ErrorObject?>> = resultFuture { api.getPlayers(names) }

    override fun getPlayers(names: Array<UUID>): CompletableFuture<Result<List<Resident>?, ErrorObject?>> = resultFuture { api.getPlayers(names) }

    override fun getPlayer(name: String): CompletableFuture<Result<Resident?, ErrorObject?>> = resultFuture { api.getPlayer(name) }

    override fun getPlayer(name: UUID): CompletableFuture<Result<Resident?, ErrorObject?>> = resultFuture { api.getPlayer(name) }

    override fun getServer(): CompletableFuture<Result<ServerInfo?, ErrorObject?>> = resultFuture { api.getServer() }

    override fun getStaff(): CompletableFuture<Result<List<SerializableUUID>?, ErrorObject?>> = resultFuture { api.getStaff() }

    override fun getNearby(query: NearbyItem): CompletableFuture<Result<List<Reference>?, ErrorObject?>> = resultFuture { api.getNearby(query) }

    override fun getVisiblePlayers(): CompletableFuture<Result<List<MapReturn>?, ErrorObject?>> = resultFuture { api.getVisiblePlayers() }

    override fun getMysteryMaster(): CompletableFuture<Result<List<MysteryMaster>?, ErrorObject?>> = resultFuture { api.getMysteryMaster() }

    override fun getLocation(query: List<Pair<Int, Int>>): CompletableFuture<Result<List<Location>?, ErrorObject?>> = resultFuture { api.getLocation(query) }

    override fun getDiscord(query: List<DiscordPayload>): CompletableFuture<Result<List<DiscordResponse>?, ErrorObject?>> = resultFuture { api.getDiscord(query) }

    override fun getOnline(): CompletableFuture<Result<List<Reference>?, ErrorObject?>> = resultFuture { api.getOnline() }
}