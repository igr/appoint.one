package routes

import cal.ICS
import domain.appointment.verbs.FindAppointmentForTimeslot
import domain.timeslot.toTimeslotId
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.header
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import server.DatabaseFactory.dbtx

fun Route.appointment() {

	route("/appointments") {
		get("/{id}") {
			val timeslotId = call.parameters["id"]?.toTimeslotId() ?: throw IllegalStateException("ID missing")

			val appointment = dbtx {
				FindAppointmentForTimeslot(timeslotId)
			}

			appointment?.let { call.respond(it) } ?: call.respond(HttpStatusCode.NotFound)
		}

		get("/{id}/ical") {
			val timeslotId = call.parameters["id"]?.toTimeslotId() ?: throw IllegalStateException("ID missing")

			val appointment = dbtx {
				FindAppointmentForTimeslot(timeslotId)
			}

			appointment?.let {
				call.response.header("Content-Disposition", "attachment; filename=\"${timeslotId}.ics\"")
				call.respondText(ICS.of(appointment))
			} ?: call.respond(HttpStatusCode.NotFound)
		}
	}
}
