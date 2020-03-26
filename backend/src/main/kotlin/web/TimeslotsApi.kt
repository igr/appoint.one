package web

import domain.Timeslots
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.route
import model.NewTimeslot

fun Route.timeslots() {

    route("/timeslots") {

        post("/") {
            val newTimeslot = call.receive<NewTimeslot>()
            call.respond(HttpStatusCode.Created, Timeslots.add(newTimeslot))
        }
    }

}
