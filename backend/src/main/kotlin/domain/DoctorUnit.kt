package domain

import DateTime
import model.DoctorEntity
import model.TimeslotEntity
import model.add
import server.DatabaseFactory.dbtx

@TargetIs("Single doctor")
class DoctorUnit internal constructor(private val doctorEntity: DoctorEntity) {

	suspend fun listTimeslots() = dbtx {
		doctorEntity.timeslots
			.sortedWith(compareBy { it.datetime })
			.reversed()
			.map { it.toTimeslot() }
	}

	suspend fun bindTimeslots(timeslotList: List<DateTime>) = dbtx {
		val existingTimeslots = listTimeslots()

		timeslotList
			.filter {
				existingTimeslots.none { existing ->
					existing.datetime == it
				}
			}
			.map {
				TimeslotEntity.add(it, doctorEntity).toTimeslot()
			}
	}

}
