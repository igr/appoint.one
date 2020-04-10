package routes

import auth.user
import domain.evaluation.NewEvaluation
import domain.timeslot.TimeslotById
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
				val newEvaluation = call.receive<NewEvaluation>()

				val evaluation = TimeslotById(newEvaluation.timeslotId)
					.assertOwnership(call.user?.id)
					.markDone(newEvaluation.data)

				call.respond(evaluation)
			}
		}
	}
}
