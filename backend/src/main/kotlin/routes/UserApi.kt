package routes

import domain.user.Password
import domain.user.toUserId
import domain.user.verbs.ChangeUserPassword
import domain.user.verbs.FindExistingUserById
import domain.user.verbs.FindUserById
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.put
import io.ktor.routing.route
import server.DatabaseFactory.dbtx

fun Route.users() {
	route("/users") {

		get("/{id}") {
			val id = call.parameters["id"]?.toUserId() ?: throw IllegalStateException("ID missing")
			val user = dbtx {
				FindUserById(id)
			}
			user?.let { call.respond(it) } ?: call.respond(HttpStatusCode.NotFound)
		}

		authenticate {
			put("/{id}/password") {
				val id = call.parameters["id"]?.toUserId() ?: throw IllegalStateException("ID missing")
				val payload = call.receive<Password>()

				val updated = dbtx {
					val user = FindExistingUserById(id)
					ChangeUserPassword(user.id, payload.password)
				}

				if (updated) call.respond(HttpStatusCode.Accepted) else call.respond(HttpStatusCode.NotFound)
			}
		}
	}
}
