package routes

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
			val result = dbtx {
				CalculateTimeslotUsageStats()
			}
			call.respond(result)
		}
	}
}
