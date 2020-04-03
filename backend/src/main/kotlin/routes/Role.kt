package routes

import domain.Doctors
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.route

fun Route.role() {

	authenticate {
		route("users") {
			post("doctor/{id}") {
				val id = call.parameters["id"]?.toInt() ?: throw IllegalStateException("ID missing")
				Doctors.findByUserId(id)
				call.respond("23")
			}
		}
	}
}
