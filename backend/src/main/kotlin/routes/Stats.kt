package routes

import domain.timeslot.TimeslotsCount
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route

fun Route.stats() {

	route("/stats") {
		get {
			val result = TimeslotsCount.stats()
			call.respond(result)
		}
	}
}
