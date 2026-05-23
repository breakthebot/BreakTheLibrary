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
package org.breakthebot.breakthelibrary.models

/**
 * Represents a response from the API.
 * @param T The type of the data if the request is successful.
 * */
sealed class ApiResult<out T> {
    /**
     * Represents a successful response from the API.
     * @param T The type of the data.
     * @param data The response data of type T.
     * @param statusCode The status code.
     * */
    data class Success<T>(
        val data: T,
        val statusCode: Int?
    ) : ApiResult<T>()

    /**
     * Represents an error sent from the API.
     * @param message The error message returned by the api.
     * @param statusCode The status code the request returned.
     * */
    data class Error(
        val message: String,
        val statusCode: Int?,
    ) : ApiResult<Nothing>()
}

/**
 * Returns [Success.data] or null.
 * */
fun <T> ApiResult<T>.getOrNull(): T? {
    return when (this) {
        is ApiResult.Success<T> -> this.data
        else -> null
    }
}

/**
 * Map data if it is a successful request.
 * @param transform The function that should be executed with the data as input.
 * */
inline fun <T, R> ApiResult<T>.mapSuccess(
    transform: (T) -> R
): ApiResult<R> {
    return when (this) {
        is ApiResult.Success -> ApiResult.Success(
            transform(data),
            statusCode
        )

        is ApiResult.Error -> this
    }
}

/** Map an action in-case of an error.
 * @param transform The function that should be executed with the error as input.
 * */
inline fun <T> ApiResult<T>.mapError(
    transform: (ApiResult.Error) -> ApiResult.Error
): ApiResult<T> {
    return when (this) {
        is ApiResult.Success -> this
        is ApiResult.Error -> transform(this)
    }
}

/**
 * Map a function on success.
 * @param block The action to execute
 * */
inline fun <T> ApiResult<T>.onSuccess(
    block: (T) -> Unit
): ApiResult<T> {
    if (this is ApiResult.Success) {
        block(data)
    }
    return this
}

/**
 * Map a function on error.
 * @param block The action to execute.
 * */
inline fun <T> ApiResult<T>.onError(
    block: (ApiResult.Error) -> Unit
): ApiResult<T> {
    if (this is ApiResult.Error) {
        block(this)
    }
    return this
}

/** Set a function to be executed on error.
 * @param fallback The function executed in-case of an error.
 * */
inline fun <T> ApiResult<T>.getOrElse(
    fallback: (ApiResult.Error) -> T
): T {
    return when (this) {
        is ApiResult.Success -> data
        is ApiResult.Error -> fallback(this)
    }
}