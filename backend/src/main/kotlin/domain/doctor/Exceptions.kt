package domain.doctor

import io.ktor.http.HttpStatusCode
import server.StatusException

class InvalidDoctorRegistrationCode : StatusException(
	message = "Registration code is invalid",
	status = HttpStatusCode.Forbidden
)
