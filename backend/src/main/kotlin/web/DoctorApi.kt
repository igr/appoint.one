package web

import domain.App.`$doctors`
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import model.NewDoctor

fun Route.doctor() {

    route("/doctor") {

        get("/") {
            call.respond(`$doctors`.findAll())
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalStateException("ID missing")
            val doctor = `$doctors`.findById(id);

            doctor?.let { call.respond(it) } ?: call.respond(HttpStatusCode.NotFound)
        }

        post("/") {
            val newDoctor = call.receive<NewDoctor>()
            call.respond(HttpStatusCode.Created, `$doctors`.add(newDoctor))
        }
    }

}
