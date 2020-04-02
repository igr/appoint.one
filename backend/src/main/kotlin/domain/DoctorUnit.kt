package domain

import model.DoctorEntity
import model.NewTimeslot
import model.TimeslotEntity
import model.add
import server.DatabaseFactory.dbtx

@TargetIs("Single doctor")
class DoctorUnit internal constructor(private val doctorEntity: DoctorEntity) {

	suspend fun listAllTimeslots() = dbtx {
		doctorEntity.timeslots
			.sortedWith(compareBy { it.datetime })
			.map { it.toTimeslot() }
	}

	suspend fun bindTimeslots(timeslotList: List<NewTimeslot>) = dbtx {
		val existingTimeslots = listAllTimeslots();

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
