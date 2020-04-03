package routes

import domain.Doctors
import domain.Timeslots
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import model.Country_SERBIA
import model.NewDoctorTimeslot

fun Route.timeslots() {

	route("/timeslots") {

		post("/") {
			val newDoctorTimeslots = call.receive<NewDoctorTimeslot>()
			val timeslots = Doctors
				.with(newDoctorTimeslots.doctorId)
				.bindTimeslots(listOf(newDoctorTimeslots.datetime))

			call.respond(HttpStatusCode.Created, timeslots)
		}

		get("/count") {
			call.respond(Timeslots.countAvailableTimeslots())
		}

		get("/available") {
			call.respond(Timeslots.findNextTimeslots(Country_SERBIA))
		}

		put("{id}/reserve") {
			val id = call.parameters["id"]?.toInt() ?: throw IllegalStateException("ID missing")
			call.respond(HttpStatusCode.NoContent, Timeslots.with(id).reserve())
		}

		get("/{id}") {
			val id = call.parameters["id"]?.toInt() ?: throw IllegalStateException("ID missing")
			val timeslot = Timeslots.findById(id)

			timeslot?.let { call.respond(it) } ?: call.respond(HttpStatusCode.NotFound)
		}

		delete("/{id}") {
			val id = call.parameters["id"]?.toInt() ?: throw IllegalStateException("ID missing")
			Timeslots.deleteById(id)

			call.respond(HttpStatusCode.Accepted)
		}

	}

}
