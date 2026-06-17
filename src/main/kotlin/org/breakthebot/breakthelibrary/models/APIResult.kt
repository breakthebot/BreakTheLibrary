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
 * @property isSuccess If the response is success.
 * @property isError If the response is error.
 *  */
sealed class APIResult<out T> {
    val isSuccess: Boolean
        get() = this is Success

    val isError: Boolean
        get() = this is Error

    /**
     * Represents a successful response from the API.
     * @param T The type of the data.
     * @param data The response data of type T.
     * @param statusCode The status code.
     * */
    open class Success<T>(
        val data: T,
        val statusCode: Int = 0,
        var db: Boolean = false
    ) : APIResult<T>()

    /**
     * Represents an error sent from the API.
     * @param message The error message returned by the api.
     * @param statusCode The status code the request returned.
     * */
    open class Error(
        val message: String,
        val statusCode: Int,
    ) : APIResult<Nothing>()

    /**
     * Returns [Success.data] or null.
     * */
    fun getOrNull(): T? {
        return when (this) {
            is Success<T> -> this.data
            else -> null
        }
    }

    /**
     * Returns [Error] or null.
     */
    fun getErrorOrNull(): Error? {
        return when (this) {
            is Error -> this
            is Success -> null
        }
    }

    /**
     * Get the status code from both success & error.
     * */
    fun getStatusCode(): Int {
        return when(this) {
            is Success -> statusCode
            is Error -> statusCode
        }
    }

    /**
     * Get the error message or empty.
     * */
    fun getErrorMessage(): String {
        return when (this) {
            is Success -> ""
            is Error -> message
        }
    }

    /**
     * Map data if it is a successful request.
     * @param transform The function that should be executed with the data as input.
     * */
    inline fun <R> mapSuccess(
        transform: (T) -> R
    ): APIResult<R> {
        return when (this) {
            is Success -> Success(
                transform(data),
                statusCode
            )

            is Error -> this
        }
    }

    /** Map an action in-case of an error.
     * @param transform The function that should be executed with the error as input.
     * */
    inline fun mapError(
        transform: (Error) -> Error
    ): APIResult<T> {
        return when (this) {
            is Success -> this
            is Error -> transform(this)
        }
    }

    /**
     * Map a function on success.
     * @param block The action to execute
     * */
    inline fun onSuccess(
        block: (T) -> Unit
    ): APIResult<T> {
        if (this is Success) {
            block(data)
        }
        return this
    }

    /**
     * Map a function on error.
     * @param block The action to execute.
     * */
    inline fun onError(
        block: (Error) -> Unit
    ): APIResult<T> {
        if (this is Error) {
            block(this)
        }
        return this
    }

    inline fun getOrElse(
        fallback: (Error) -> @UnsafeVariance T
    ): T = when (this) {
        is Success -> data
        is Error -> fallback(this)
    }
}