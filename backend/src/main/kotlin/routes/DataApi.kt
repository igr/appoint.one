package routes

import domain.city.CitiesList
import domain.country.Countries
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route

fun Route.data() {

	route("/data") {

		get("/cities") {
			call.respond(CitiesList.allCities())
		}

		get("/countries") {
			call.respond(Countries.values.sortedBy { it.id })
		}

	}

}
