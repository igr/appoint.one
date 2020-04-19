package routes

import auth.Auth
import auth.UserNotFound
import auth.user
import domain.user.verbs.FindUserByUsername
import io.ktor.application.call
import io.ktor.auth.UserPasswordCredential
import io.ktor.auth.authenticate
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import server.DatabaseFactory.dbtx

fun Route.auth() {

	route("/login") {
		post {
			val credential = call.receive<UserPasswordCredential>()
			val user = Auth.login(credential)
			call.respond(user)
		}
	}

	route("/user") {
		authenticate {
			get {
				val (_, name, _, _, token) = call.user!!
				val user = dbtx {
					FindUserByUsername(name)?.copy(token = token) ?: throw UserNotFound
				}
				call.respond(user)
			}

//			put {
//				val current = call.user!!
//				val new = call.receive<UserWrapper>().user
//				val updated = authService.updateUser(new, current)
//				call.respond(UserWrapper(updated))
//			}
		}
	}
}
