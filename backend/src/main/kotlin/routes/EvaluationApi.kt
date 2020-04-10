package routes

import domain.evaluation.NewEvaluation
import domain.timeslot.TimeslotByIdStatus
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.route

fun Route.evaluations() {

	route("/evaluations") {
		authenticate {
			post {
//				call.user
				val newEvaluation = call.receive<NewEvaluation>()

				val evaluation = TimeslotByIdStatus(newEvaluation.timeslotId).markDone(newEvaluation.data)

				call.respond(evaluation)
			}
		}
	}
}
