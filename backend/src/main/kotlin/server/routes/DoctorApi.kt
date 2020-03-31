package server.routes

import domain.Doctors
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import model.DoctorId
import model.NewDoctor

fun Route.doctors() {

    route("/doctors") {

        get {
            call.respond(Doctors.findAllDoctors())
        }

        get("/{id}") {
	        val id = call.parameters["id"]?.toInt() ?: throw IllegalStateException("ID missing")
	        val doctor = Doctors.findById(DoctorId(id));

	        doctor?.let { call.respond(it) } ?: call.respond(HttpStatusCode.NotFound)
        }

        post {
            val newDoctor = call.receive<NewDoctor>()
            call.respond(HttpStatusCode.Created, Doctors.addNewDoctor(newDoctor))
        }
    }

}
