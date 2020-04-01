package routes

import domain.Doctors
import domain.Timeslots
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import model.Country
import model.NewDoctorTimeslots

fun Route.timeslots() {

    route("/timeslots") {

	    post("/") {
		    val newDoctorTimeslots = call.receive<NewDoctorTimeslots>()

		    call.respond(HttpStatusCode.Created,
			    Doctors
				    .with(newDoctorTimeslots.doctorId)
				    .bindTimeslots(newDoctorTimeslots.timeslots))
	    }

	    get("/count") {
		    call.respond(Timeslots.countAvailableTimeslots())
	    }

	    get("/available") {
		    call.respond(Timeslots.findNextTimeslots(Country.SERBIA))
	    }
    }

}
