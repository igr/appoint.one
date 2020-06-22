package routes

import domain.Ctx
import domain.doctor.verbs.ListAllDoctorsWithStats
import domain.timeslot.verbs.CalculateTimeslotUsageStats
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.ContentDisposition
import io.ktor.http.HttpHeaders
import io.ktor.response.header
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import jodd.util.CsvUtil
import server.DatabaseFactory.dbtx

fun Route.stats() {

	route("/stats") {
		get {
			dbtx {
				Ctx.of(Unit)
					.set(CalculateTimeslotUsageStats)
			}.use {
				call.respond(it)
			}
		}
	}

	route("/stats/doctors") {
		authenticate {
			get {
				val evaluations = dbtx {
					ListAllDoctorsWithStats()
				}

				var csv = ""
				evaluations.forEach {
					csv += CsvUtil.toCsvString(
						it.newStatusCount,
						it.canceledStatusCount,
						it.doneStatusCount,
						it.reservedStatusCount,
						it.doctor.id,
						it.doctor.data.name,
						it.doctor.data.email,
						it.doctor.data.phone
					) + '\n'
				}

				call.response.header(
					HttpHeaders.ContentDisposition,
					ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, "doctors.csv").toString())
				call.respond(csv)
			}
		}
	}

}
