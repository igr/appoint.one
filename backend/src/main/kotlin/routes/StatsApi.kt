package routes

import domain.Ctx
import domain.timeslot.verbs.CalculateTimeslotUsageStats
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import server.DatabaseFactory.dbtx

fun Route.stats() {

	route("/stats") {
		get {
			dbtx {
				Ctx.of(Unit)
					.set(CalculateTimeslotUsageStats)
			}.useS {
				call.respond(it)
			}

		}
	}
}
