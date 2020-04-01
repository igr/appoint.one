package routes

import domain.Cities
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import model.Country

fun Route.data() {

	route("/data") {

		get("/cities") {
			call.respond(Cities.findAllCities())
		}

		get("/countries") {
			call.respond(Country.valuesAsMap())
		}

	}

}
