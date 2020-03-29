package server.routes

import io.ktor.application.call
import io.ktor.pebble.PebbleContent
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route

fun Route.index() {

	route("/") {
		get {
			call.respond(PebbleContent("index.peb", mapOf()))
		}
	}
}
