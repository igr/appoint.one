package routes

import domain.doctor.DoctorById
import domain.doctor.DoctorEnabler
import domain.doctor.DoctorTimeslots
import domain.doctor.DoctorsLists
import domain.user.NewDoctorUser
import domain.user.Users
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*

fun Route.doctors() {

	route("/doctors") {

		get {
			call.respond(DoctorsLists.allDoctorsOrdered())
		}

		post {
			val newDoctorAndUser = call.receive<NewDoctorUser>()
			val doctor = Users.addAndGetDoctor(newDoctorAndUser)
			call.respond(HttpStatusCode.Created, doctor)
		}

		get("/{id}") {
			val id = call.parameters["id"]?.toInt() ?: throw IllegalStateException("ID missing")
			val doctor = DoctorById(id).get()
			doctor?.let { call.respond(it) } ?: call.respond(HttpStatusCode.NotFound)
		}

		get("/{id}/timeslots") {
			val id = call.parameters["id"]?.toInt() ?: throw IllegalStateException("ID missing")
			val timeslots = DoctorTimeslots(id).listTimeslots()
			call.respond(timeslots)
		}

		put("/{id}/enable") {
			val doctorId = call.parameters["id"]?.toInt() ?: throw IllegalStateException("ID missing")
			DoctorEnabler(doctorId).confirmDoctor()
			call.respond(HttpStatusCode.NoContent)
		}

		put("/{id}/disable") {
			val doctorId = call.parameters["id"]?.toInt() ?: throw IllegalStateException("ID missing")
			DoctorEnabler(doctorId).unconfirmDoctor()
			call.respond(HttpStatusCode.NoContent)
		}
	}

}
