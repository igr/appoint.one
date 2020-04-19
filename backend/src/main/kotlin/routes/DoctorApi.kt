package routes

import auth.user
import domain.Ctx
import domain.doctor.DoctorData
import domain.doctor.toDoctorId
import domain.doctor.verbs.*
import domain.user.NewDoctorUser
import domain.user.verbs.AddDoctor
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import server.DatabaseFactory.dbtx

fun Route.doctors() {

	route("/doctors") {

		get {
			dbtx {
				call.respond(ListDoctorsOrdered())
			}
		}

		get("/{id}") {
			val doctorId = call.parameters["id"]?.toDoctorId() ?: throw IllegalStateException("ID missing")
			val doctor = dbtx {
				FindDoctorById(doctorId)
			}
			doctor?.let { call.respond(it) } ?: call.respond(HttpStatusCode.NotFound)
		}

		get("/{id}/timeslots") {
			val doctorId = call.parameters["id"]?.toDoctorId() ?: throw IllegalStateException("ID missing")
			val timeslots = dbtx {
				ListDoctorsTimeslots(doctorId)
			}
			call.respond(timeslots)
		}

		post {
			val newDoctorAndUser = call.receive<NewDoctorUser>()

			dbtx {
				Ctx.of(newDoctorAndUser)
					.map(AddDoctor)
					.map(FindExistingDoctorById)
			}.useS {
				call.respond(HttpStatusCode.Created, it)
			}
		}

		authenticate {
			put("/{id}") {
				val doctorId = call.parameters["id"]?.toDoctorId() ?: throw IllegalStateException("ID missing")
				val doctorData = call.receive<DoctorData>()

				dbtx {
					Ctx.of(doctorId)
						.run(AssertDoctorIsUser) { call.user?.id }
						.map(UpdateDoctorData) { doctorData }
				}.useS {
					call.respond(HttpStatusCode.Accepted)
				}
			}
		}
	}

}
