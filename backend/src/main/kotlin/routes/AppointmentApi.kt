package routes

import domain.appointment.AppointmentByTimeslot
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route

fun Route.appointment() {

	route("/appointments") {
		get("/{id}") {
			val id = call.parameters["id"]?.toInt() ?: throw IllegalStateException("ID missing")

			val appointment = AppointmentByTimeslot(id).get()

			appointment?.let { call.respond(it) } ?: call.respond(HttpStatusCode.NotFound)
		}
	}
}
