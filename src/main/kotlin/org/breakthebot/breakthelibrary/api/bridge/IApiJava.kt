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
import org.breakthebot.breakthelibrary.utils.SerializableUUID
import java.util.UUID
import java.util.concurrent.CompletableFuture
import org.breakthebot.breakthelibrary.api.IApi

/**
 * Java compatible wrapper, not to be confused with [IApi]
 * */
interface IApiJava {
    fun getTowns(): CompletableFuture<Result<List<Reference>?, ErrorObject?>>

    fun getTowns(names: List<String>): CompletableFuture<Result<List<Town>?, ErrorObject?>>

    fun getTowns(names: Array<UUID>): CompletableFuture<Result<List<Town>?, ErrorObject?>>

    fun getTown(name: String): CompletableFuture<Result<Town?, ErrorObject?>>

    fun getTown(name: UUID): CompletableFuture<Result<Town?, ErrorObject?>>

    fun getNations(): CompletableFuture<Result<List<Reference>?, ErrorObject?>>

    fun getNations(names: List<String>): CompletableFuture<Result<List<Nation>?, ErrorObject?>>

    fun getNations(names: Array<UUID>): CompletableFuture<Result<List<Nation>?, ErrorObject?>>

    fun getNation(name: String): CompletableFuture<Result<Nation?, ErrorObject?>>

    fun getNation(name: UUID): CompletableFuture<Result<Nation?, ErrorObject?>>

    fun getPlayers(): CompletableFuture<Result<List<Reference>?, ErrorObject?>>

    fun getPlayers(names: List<String>): CompletableFuture<Result<List<Resident>?, ErrorObject?>>

    fun getPlayers(names: Array<UUID>): CompletableFuture<Result<List<Resident>?, ErrorObject?>>

    fun getPlayer(name: String): CompletableFuture<Result<Resident?, ErrorObject?>>

    fun getPlayer(name: UUID): CompletableFuture<Result<Resident?, ErrorObject?>>

    fun getServer(): CompletableFuture<Result<ServerInfo?, ErrorObject?>>

    fun getStaff(): CompletableFuture<Result<List<SerializableUUID>?, ErrorObject?>>

    fun getNearby(query: NearbyItem): CompletableFuture<Result<List<Reference>?, ErrorObject?>>

    fun getVisiblePlayers(): CompletableFuture<Result<List<MapReturn>?, ErrorObject?>>

    fun getMysteryMaster(): CompletableFuture<Result<List<MysteryMaster>?, ErrorObject?>>

    fun getLocation(query: List<Pair<Int, Int>>): CompletableFuture<Result<List<Location>?, ErrorObject?>>

    fun getDiscord(query: List<DiscordPayload>): CompletableFuture<Result<List<DiscordResponse>?, ErrorObject?>>

    fun getOnline(): CompletableFuture<Result<List<Reference>?, ErrorObject?>>
}