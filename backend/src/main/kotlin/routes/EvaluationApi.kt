package routes

import auth.user
import domain.Ctx
import domain.evaluation.NewEvaluation
import domain.evaluation.verbs.ListAllEvaluations
import domain.timeslot.verbs.AssertTimeslotIsOwnedByUser
import domain.timeslot.verbs.MarkTimeslotAsDone
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import server.DatabaseFactory.dbtx

fun Route.evaluations() {

	route("/evaluations") {
		authenticate {
			get {
				val evaluations = dbtx {
					ListAllEvaluations()
				}

				call.respond(evaluations)
			}

			post {
				val newEvaluation = call.receive<NewEvaluation>()

				dbtx {
					Ctx.of(newEvaluation.timeslotId)
						.run(AssertTimeslotIsOwnedByUser) { call.user?.id }
						.map(MarkTimeslotAsDone) { newEvaluation.data }
				}.useS {
					call.respond(HttpStatusCode.Created, it)
				}
			}
		}
	}
}
