package domain.user

import io.ktor.http.HttpStatusCode

class AccessDenied : Exception() {
	val status = HttpStatusCode.Forbidden
}
