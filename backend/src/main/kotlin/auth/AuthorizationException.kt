package auth

import io.ktor.http.HttpStatusCode

class AuthorizationException : Exception() {
    val status = HttpStatusCode.Forbidden
}