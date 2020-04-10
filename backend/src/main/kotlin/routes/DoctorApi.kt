package routes

import domain.doctor.DoctorById
import domain.doctor.DoctorTimeslots
import domain.doctor.DoctorsLists
import domain.doctor.toDoctorId
import domain.user.NewDoctorUser
import domain.user.Users
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route

fun Route.doctors() {

	route("/doctors") {

		get {
			call.respond(DoctorsLists.allDoctorsOrdered())
		}

		get("/{id}") {
			val id = call.parameters["id"]?.toDoctorId() ?: throw IllegalStateException("ID missing")
			val doctor = DoctorById(id).get()
			doctor?.let { call.respond(it) } ?: call.respond(HttpStatusCode.NotFound)
		}

		get("/{id}/timeslots") {
			val id = call.parameters["id"]?.toDoctorId() ?: throw IllegalStateException("ID missing")
			val timeslots = DoctorTimeslots(id).listTimeslots()
			call.respond(timeslots)
		}

		post {
			val newDoctorAndUser = call.receive<NewDoctorUser>()
			val doctor = Users.addAndGetDoctor(newDoctorAndUser)
			call.respond(HttpStatusCode.Created, doctor)
		}
	}

}
