package routes

import auth.user
import domain.doctor.*
import domain.user.NewDoctorUser
import domain.user.Users
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*

fun Route.doctors() {

	route("/doctors") {

		get {
			call.respond(DoctorsLists.allDoctorsOrdered())
		}

		get("/{id}") {
			val doctorId = call.parameters["id"]?.toDoctorId() ?: throw IllegalStateException("ID missing")
			val doctor = DoctorById(doctorId).get()
			doctor?.let { call.respond(it) } ?: call.respond(HttpStatusCode.NotFound)
		}

		get("/{id}/timeslots") {
			val doctorId = call.parameters["id"]?.toDoctorId() ?: throw IllegalStateException("ID missing")
			val timeslots = DoctorTimeslots(doctorId).listTimeslots()
			call.respond(timeslots)
		}

		post {
			val newDoctorAndUser = call.receive<NewDoctorUser>()
			val doctor = Users.addAndGetDoctor(newDoctorAndUser)
			call.respond(HttpStatusCode.Created, doctor)
		}

		authenticate {
			put("/{id}") {
				val doctorId = call.parameters["id"]?.toDoctorId() ?: throw IllegalStateException("ID missing")
				val doctorData = call.receive<DoctorData>()

				DoctorById(doctorId)
					.assertUser(call.user?.id)
					.update(doctorData);

				call.respond(HttpStatusCode.Accepted)
			}
		}
	}

}
