package domain

import infra.DatabaseFactory.dbtx
import model.DoctorEntity
import model.NewTimeslot
import model.TimeslotEntity

class DoctorUnit internal constructor(private val _doctor: DoctorEntity) {

	suspend fun listTimeslots() = dbtx {
		_doctor.timeslots
			.sortedWith(compareBy<TimeslotEntity> { it.date }
				.thenBy { it.time })
			.map { it.toTimeslot() }
	}

	suspend fun bindTimeslots(timeslotList: List<NewTimeslot>) = dbtx {
		val existingTimeslots = listTimeslots();

		timeslotList
			.filter {
				existingTimeslots.none { existing ->
					it.date == existing.date &&
						it.time == existing.time
				}
			}
			.forEach {
				TimeslotEntity.new {
					date = it.date
					time = it.time
					doctor = _doctor
				}
			}
	}

}
