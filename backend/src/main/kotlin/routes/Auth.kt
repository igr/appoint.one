package routes

import auth.Auth
import auth.UserNotFound
import auth.user
import domain.Users
import io.ktor.application.call
import io.ktor.auth.UserPasswordCredential
import io.ktor.auth.authenticate
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import model.NewUser

fun Route.auth() {

	route("users") {

		post("/login") {
			val credential = call.receive<UserPasswordCredential>()
			val user = Auth.login(credential);
			call.respond(user)
		}

		// todo prevent easy user creation
		post {
			val newUser = call.receive<NewUser>()
			val user = Auth.register(newUser)
			call.respond(user)
		}
	}

	authenticate {
		route("user") {
			get {
				val (_, name, _, _, token) = call.user!!

				println(name)

				val user = Users.findUserByUsername(name)?.copy(token = token) ?: throw UserNotFound
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
