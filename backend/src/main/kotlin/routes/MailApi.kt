package routes

import domain.admin.verbs.SendTestMail
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.route
import scheduler.Scheduler

fun Route.mails() {

	route("/mails") {
		authenticate {
			post {
				Scheduler.registerTask { SendTestMail() }
				call.respond(HttpStatusCode.Created)
			}
		}
	}
}
