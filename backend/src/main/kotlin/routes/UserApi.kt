package routes

import domain.user.*
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.request.receiveText
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route

fun Route.users() {
	route("/users") {

		get("/{id}") {
			val id = call.parameters["id"]?.toUserId() ?: throw IllegalStateException("ID missing")
			val user = UserById(id).get()
			user?.let { call.respond(it) } ?: call.respond(HttpStatusCode.NotFound)
		}

		post("/modifyUserData") {
			val newDoctorAndUser = call.receive<changedUserPassword>()
			val result = Users.changeUserPassword(newDoctorAndUser)
			call.respond(HttpStatusCode.OK, Users.changeUserPassword(newDoctorAndUser))
			//result?.let { call.respond(it) } ?: call.respond(HttpStatusCode.NotFound)
		}
	}
}
