package routes

import auth.Auth
import auth.UserNotFound
import auth.user
import domain.user.UserByUsername
import io.ktor.application.call
import io.ktor.auth.UserPasswordCredential
import io.ktor.auth.authenticate
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route

fun Route.auth() {

	route("users") {

		post("/login") {
			val credential = call.receive<UserPasswordCredential>()
			val user = Auth.login(credential)
			call.respond(user)
		}

		authenticate {
			get {
				val (_, name, _, _, token) = call.user!!
				val user = UserByUsername(name).get()?.copy(token = token) ?: throw UserNotFound
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
