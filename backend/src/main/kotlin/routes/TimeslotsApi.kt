package routes

import DateTime
import auth.user
import domain.doctor.verbs.BindTimeslotsToDoctor
import domain.timeslot.NewTimeslot
import domain.timeslot.toTimeslotId
import domain.timeslot.verbs.*
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import server.DatabaseFactory.dbtx

// todo old-school here
fun Route.timeslots() {

	route("/timeslots") {

		get("/count") {
			val count = dbtx {
				CountAvailableTimeslots()
			}
			call.respond(count)
		}

		get("/available") {
			val date = call.parameters["date"]
			val from = if (date != null) DateTime.ofDate(date) else DateTime.now()

			val list = dbtx {
				DetermineNextAvailableTimeslots.uptTo10(from)
			}

			call.respond(list)
		}

		get("/{id}") {
			val id = call.parameters["id"]?.toTimeslotId() ?: throw IllegalStateException("ID missing")

			val timeslot = dbtx { FindTimeslotById(id) }

			timeslot?.let { call.respond(it) } ?: call.respond(HttpStatusCode.NotFound)
		}

		put("{id}/reserve") {
			val id = call.parameters["id"]?.toTimeslotId() ?: throw IllegalStateException("ID missing")

			dbtx { ReserveTimeslotIfNew(id) }

			call.respond(HttpStatusCode.Accepted)
		}

		authenticate {
			post("/") {
				val newDoctorTimeslots = call.receive<NewTimeslot>()

				val timeslots = dbtx {
					BindTimeslotsToDoctor(
						newDoctorTimeslots.doctorId,
						listOf(newDoctorTimeslots.datetime)
					)
				}
				call.respond(HttpStatusCode.Created, timeslots)
			}

			put("{id}/cancel") {
				val timeslotId = call.parameters["id"]?.toTimeslotId() ?: throw IllegalStateException("ID missing")

				dbtx {
					AssertTimeslotIsOwnedByUser(timeslotId, call.user?.id)
					CancelTimeslotIfReserved(timeslotId)
				}

				call.respond(HttpStatusCode.Accepted)
			}

			put("{id}/activate") {
				val timeslotId = call.parameters["id"]?.toTimeslotId() ?: throw IllegalStateException("ID missing")
				dbtx {
					AssertTimeslotIsOwnedByUser(timeslotId, call.user?.id)
					ActivateTimeslotIfReserved(timeslotId)
				}

				call.respond(HttpStatusCode.Accepted)
			}

			delete("/{id}") {
				val timeslotId = call.parameters["id"]?.toTimeslotId() ?: throw IllegalStateException("ID missing")

				dbtx {
					AssertTimeslotIsOwnedByUser(timeslotId, call.user?.id)
					DeleteTimeslot(timeslotId)
				}

				call.respond(HttpStatusCode.Accepted)
			}
		}

	}
}
