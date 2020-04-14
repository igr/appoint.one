package routes

import DateTime
import auth.user
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
			call.respond(TimeslotsCount.countAvailableTimeslots())
		}

		get("/available") {
			val date = call.parameters["date"]

			val ts = TimeslotsNextSet();
			val list = if (date == null) ts.get() else ts.from(DateTime.ofDate(date))

			call.respond(list)
		}

		get("/{id}") {
			val id = call.parameters["id"]?.toTimeslotId() ?: throw IllegalStateException("ID missing")
			val timeslot = TimeslotById(id).get()

			timeslot?.let { call.respond(it) } ?: call.respond(HttpStatusCode.NotFound)
		}

		put("{id}/reserve") {
			val id = call.parameters["id"]?.toTimeslotId() ?: throw IllegalStateException("ID missing")
			call.respond(TimeslotById(id).reserveIfNew())
		}

		authenticate {
			post("/") {
				val newDoctorTimeslots = call.receive<NewTimeslot>()

				val timeslots = DoctorTimeslots(newDoctorTimeslots.doctorId)
					.bindAndReturnTimeslots(listOf(newDoctorTimeslots.datetime))

				call.respond(HttpStatusCode.Created, timeslots)
			}

			put("{id}/cancel") {
				val id = call.parameters["id"]?.toTimeslotId() ?: throw IllegalStateException("ID missing")
				val count = TimeslotById(id)
					.assertOwnership(call.user?.id)
					.cancelIfReserved()

				call.respond(HttpStatusCode.NoContent, count)
			}

			put("{id}/activate") {
				val id = call.parameters["id"]?.toTimeslotId() ?: throw IllegalStateException("ID missing")
				val count = TimeslotById(id)
					.assertOwnership(call.user?.id)
					.activateIfReserved()

				call.respond(HttpStatusCode.NoContent, count)
			}

			delete("/{id}") {
				val id = call.parameters["id"]?.toTimeslotId() ?: throw IllegalStateException("ID missing")
				TimeslotById(id)
					.assertOwnership(call.user?.id)
					.delete()

				call.respond(HttpStatusCode.Accepted)
			}
		}

	}
}
