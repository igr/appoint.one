package routes

import domain.doctor.DoctorTimeslots
import domain.timeslot.*
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*

fun Route.timeslots() {

	route("/timeslots") {

		get("/count") {
			call.respond(TimeslotsCount.availableTimeslots())
		}

		get("/available") {
			call.respond(TimeslotsNextSet.get())
		}

		get("/{id}") {
			val id = call.parameters["id"]?.toInt() ?: throw IllegalStateException("ID missing")
			val timeslot = TimeslotById(id).get()

			timeslot?.let { call.respond(it) } ?: call.respond(HttpStatusCode.NotFound)
		}

		// security issue

		put("{id}/reserve") {
			val id = call.parameters["id"]?.toInt() ?: throw IllegalStateException("ID missing")
			call.respond(HttpStatusCode.NoContent, TimeslotStatusUpdater(id).reserveIfNew())
		}

		authenticate {
			post("/") {
				val newDoctorTimeslots = call.receive<NewTimeslot>()

				val timeslots = DoctorTimeslots(newDoctorTimeslots.doctorId)
					.bindAndReturnTimeslots(listOf(newDoctorTimeslots.datetime))

				call.respond(HttpStatusCode.Created, timeslots)
			}

			put("{id}/cancel") {
				val id = call.parameters["id"]?.toInt() ?: throw IllegalStateException("ID missing")
				call.respond(HttpStatusCode.NoContent, TimeslotStatusUpdater(id).cancelIfReserved())
			}

			delete("/{id}") {
				val id = call.parameters["id"]?.toInt() ?: throw IllegalStateException("ID missing")
				TimeslotById(id).delete()

				call.respond(HttpStatusCode.Accepted)
			}
		}

	}
}
