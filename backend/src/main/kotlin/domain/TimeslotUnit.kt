package domain

import io.ktor.http.HttpStatusCode
import model.TimeslotEntity
import model.TimeslotStatus
import server.DatabaseFactory
import server.StatusException

@TargetIs("Single timeslot")
class TimeslotUnit internal constructor(private val timeslotEntity: TimeslotEntity) {

	suspend fun reserve() = DatabaseFactory.dbtx {
		if (timeslotEntity.status != TimeslotStatus.NEW.value) {
			throw StatusException(status = HttpStatusCode.Conflict, message = "Invalid state.")
		}
		timeslotEntity.status = TimeslotStatus.RESERVED.value
	}

}

