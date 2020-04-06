package routes

import domain.evaluation.NewEvaluation
import domain.timeslot.TimeslotStatusUpdater
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.route

fun Route.evaluations() {

	route("/evaluations") {
		post {
			val newEvaluation = call.receive<NewEvaluation>()

			val evaluation = TimeslotStatusUpdater(newEvaluation.timeslotId).markDone(newEvaluation.data)

			call.respond(evaluation)
		}
	}
}
