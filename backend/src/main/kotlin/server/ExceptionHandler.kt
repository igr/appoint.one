package server

import auth.AuthenticationException
import auth.AuthorizationException
import io.ktor.application.call
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond

fun Throwable.toMap() = mapOf(
	"error" to this.message
)

fun StatusPages.Configuration.setup() {
	exception<Throwable> { internal ->
		val status = when (internal) {
			is AuthenticationException -> internal.status
			is AuthorizationException -> internal.status
			is StatusException -> internal.status
			else -> HttpStatusCode.InternalServerError
		}

		when {
			status.value.toString().startsWith("5") -> {
				serverLogger.error(internal.message, internal)
				call.respond(status, InternalServerError.toMap())
			}
			else -> {
				serverLogger.warn(internal.message)
				call.respond(status, internal.toMap())
			}
		}
    }
}

val InternalServerError =
    StatusException("Sorry, we encountered an error and are working on it.", HttpStatusCode.InternalServerError)

open class StatusException(
    message: String?,
    open val status: HttpStatusCode = HttpStatusCode.InternalServerError,
    cause: Throwable? = null
) : Exception(message, cause)
