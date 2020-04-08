package routes

import domain.user.Password
import domain.user.UserById
import domain.user.toUserId
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.put
import io.ktor.routing.route

fun Route.users() {
	route("/users") {

		get("/{id}") {
			val id = call.parameters["id"]?.toUserId() ?: throw IllegalStateException("ID missing")
			val user = UserById(id).get()
			user?.let { call.respond(it) } ?: call.respond(HttpStatusCode.NotFound)
		}

		authenticate {
			put("/{id}/password") {
				val id = call.parameters["id"]?.toUserId() ?: throw IllegalStateException("ID missing")
				val payload = call.receive<Password>()
				val result = UserById(id).changePassword(payload.password)
				if (result == 1) call.respond(HttpStatusCode.Accepted) else call.respond(HttpStatusCode.NotFound)
			}
		}
	}
}
