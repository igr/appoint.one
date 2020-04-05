package routes

import domain.country.Country_SERBIA
import domain.doctor.DoctorTimeslots
import domain.timeslot.*
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*

fun Route.timeslots() {

	route("/timeslots") {

		post("/") {
			val newDoctorTimeslots = call.receive<NewTimeslot>()

			val timeslots = DoctorTimeslots(newDoctorTimeslots.doctorId)
				.bindAndReturnTimeslots(listOf(newDoctorTimeslots.datetime))

			call.respond(HttpStatusCode.Created, timeslots)
		}

		get("/count") {
			call.respond(TimeslotsCount.availableTimeslots())
		}

		get("/available") {
			call.respond(TimeslotsNextSet.byCountry(Country_SERBIA))
		}

		put("{id}/reserve") {
			val id = call.parameters["id"]?.toInt() ?: throw IllegalStateException("ID missing")
			call.respond(HttpStatusCode.NoContent, TimeslotStatusUpdater(id).reserveIfNew())
		}

		get("/{id}") {
			val id = call.parameters["id"]?.toInt() ?: throw IllegalStateException("ID missing")
			val timeslot = TimeslotById(id).get()

			timeslot?.let { call.respond(it) } ?: call.respond(HttpStatusCode.NotFound)
		}

		delete("/{id}") {
			val id = call.parameters["id"]?.toInt() ?: throw IllegalStateException("ID missing")
			TimeslotById(id).delete()

			call.respond(HttpStatusCode.Accepted)
		}

	}

}
