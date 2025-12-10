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
package org.breakthebot.breakthelibrary.api

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
import org.breakthebot.breakthelibrary.objects.ServerInfo
import org.breakthebot.breakthelibrary.objects.Town
import org.breakthebot.breakthelibrary.utils.SerializableUUID
import org.breakthebot.breakthelibrary.objects.Result
import java.util.UUID

/**
 * Api interface, note this is only compatible with kotlin.
 * */
interface IApi {

    suspend fun getTowns(): Result<List<Reference>?, ErrorObject?>
    suspend fun getTowns(names: List<String>): Result<List<Town>?, ErrorObject?>
    suspend fun getTowns(names: Array<UUID>): Result<List<Town>?, ErrorObject?>
    suspend fun getTown(name: String): Result<Town?, ErrorObject?>
    suspend fun getTown(name: UUID): Result<Town?, ErrorObject?>

    suspend fun getNations(): Result<List<Reference>?, ErrorObject?>
    suspend fun getNations(names: List<String>): Result<List<Nation>?, ErrorObject?>
    suspend fun getNations(names: Array<UUID>): Result<List<Nation>?, ErrorObject?>
    suspend fun getNation(name: String): Result<Nation?, ErrorObject?>
    suspend fun getNation(name: UUID): Result<Nation?, ErrorObject?>

    suspend fun getPlayers(): Result<List<Reference>?, ErrorObject?>
    suspend fun getPlayers(names: List<String>): Result<List<Resident>?, ErrorObject?>
    suspend fun getPlayers(names: Array<UUID>): Result<List<Resident>?, ErrorObject?>
    suspend fun getPlayer(name: String): Result<Resident?, ErrorObject?>
    suspend fun getPlayer(name: UUID): Result<Resident?, ErrorObject?>

    suspend fun getServer(): Result<ServerInfo?, ErrorObject?>

    suspend fun getStaff(): Result<List<SerializableUUID>?, ErrorObject?>

    suspend fun getNearby(query: NearbyItem): Result<List<Reference>?, ErrorObject?>

    suspend fun getVisiblePlayers(): Result<List<MapReturn>?, ErrorObject?>

    suspend fun getMysteryMaster(): Result<List<MysteryMaster>?, ErrorObject?>

    suspend fun getLocation(query: List<Pair<Int, Int>>): Result<List<Location>?, ErrorObject?>

    suspend fun getDiscord(query: List<DiscordPayload>): Result<List<DiscordResponse>?, ErrorObject?>

    suspend fun getOnline(): Result<List<Reference>?, ErrorObject?>
}
