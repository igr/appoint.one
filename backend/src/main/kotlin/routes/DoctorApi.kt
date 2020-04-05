package routes

import domain.Doctors
import domain.Users
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import model.NewDoctorUser

fun Route.doctors() {

	route("/doctors") {

		get {
			call.respond(Doctors.listAllDoctors())
		}

		post {
			val newDoctorAndUser = call.receive<NewDoctorUser>()
			call.respond(HttpStatusCode.Created, Users.addNewDoctorUser(newDoctorAndUser))
		}

		get("/{id}") {
			val id = call.parameters["id"]?.toInt() ?: throw IllegalStateException("ID missing")
			val doctor = Doctors.findById(id);

			doctor?.let { call.respond(it) } ?: call.respond(HttpStatusCode.NotFound)
		}

		get("/{id}/timeslots") {
			val id = call.parameters["id"]?.toInt() ?: throw IllegalStateException("ID missing")

			call.respond(Doctors.with(id).listTimeslots())
		}

		put("/{id}/enable") {
			val id = call.parameters["id"]?.toInt() ?: throw IllegalStateException("ID missing")
			Doctors.with(id).enable(true)
			call.respond(HttpStatusCode.Accepted)
		}

		put("/{id}/disable") {
			val id = call.parameters["id"]?.toInt() ?: throw IllegalStateException("ID missing")
			Doctors.with(id).enable(false)
			call.respond(HttpStatusCode.Accepted)
		}
	}

}
