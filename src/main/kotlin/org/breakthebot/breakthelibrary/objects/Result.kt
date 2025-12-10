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

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.CoroutineContext

data class Ok<out L>(val value: L) : Result<L, Nothing>
data class Err<out R>(val value: R) : Result<Nothing, R>

private val resultFutureScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

sealed interface Result<out L, out R> {
    companion object {
        @JvmStatic fun <L> ok(value: L): Result<L, Nothing> = Ok(value)
        @JvmStatic fun <R> err(value: R): Result<Nothing, R> = Err(value)
    }
}

fun <L, R, T> Result<L, R>.mapSuccess(transform: (L) -> T): Result<T, R> =
    when (this) {
        is Ok -> Ok(transform(value))
        is Err -> this
    }

fun <L, R, T> Result<L, R>.mapError(transform: (R) -> T): Result<L, T> =
    when (this) {
        is Ok -> this
        is Err -> Err(transform(value))
    }

fun <L, R> Result<L, R>.getOrNull(): L? =
    when (this) {
        is Ok -> value
        is Err -> null
    }

fun <L, R> Result<L, R>.getOrThrow(): L =
    when (this) {
        is Ok -> value
        is Err -> throw IllegalStateException("Unexpected error.")
    }

fun <L, R> Result<L, R>.getOrThrow(message: String): L =
    when (this) {
        is Ok -> value
        is Err -> throw IllegalStateException(message)
    }


fun <L, R> Result<L,R>.getErr(): R =
        when (this) {
            is Ok -> throw IllegalStateException("Expected to have failed.")
            is Err -> value
        }

fun <L, R> resultFuture(
    context: CoroutineContext = Dispatchers.Default,
    block: suspend () -> Result<L, R>
): CompletableFuture<Result<L, R>> = CoroutineScope(resultFutureScope.coroutineContext + context).future { block() }
