package domain.user

import io.ktor.http.HttpStatusCode

class AccessDenied : Exception("Access to the resource is forbidden.") {
	val status = HttpStatusCode.Forbidden
}
