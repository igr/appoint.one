package routes

import auth.user
import domain.Ctx
import domain.evaluation.NewEvaluation
import domain.evaluation.verbs.ListAllEvaluations
import domain.timeslot.verbs.AssertTimeslotIsOwnedByUser
import domain.timeslot.verbs.MarkTimeslotAsDone
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.ContentDisposition
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.header
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import jodd.util.CsvUtil
import server.DatabaseFactory.dbtx

fun Route.evaluations() {

	route("/evaluations/csv") {
		authenticate {
			get {
				val evaluations = dbtx {
					ListAllEvaluations()
				}

				var csv = ""
				evaluations.forEach {
					csv += CsvUtil.toCsvString(
						it.id,
						it.data.age,
						it.data.comment,
						it.data.forward,
						it.data.help,
						it.data.problem,
						it.data.sex,
						it.data.success
					) + '\n'
				}

				call.response.header(
					HttpHeaders.ContentDisposition,
					ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, "evaluations.csv").toString())
				call.respond(csv)
			}
		}
	}

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
				}.use {
					call.respond(HttpStatusCode.Created, it)
				}
			}
		}
	}
}
