package auth

import io.ktor.http.HttpStatusCode

open class AuthenticationException(val status: HttpStatusCode, override val message: String) : Exception()

val UserNotFound = AuthenticationException(HttpStatusCode.NotFound, "The specified user could not be found")

val UserAlreadyExists = AuthenticationException(HttpStatusCode.Conflict, "The specified user already exists")