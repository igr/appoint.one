package domain

import infra.DatabaseFactory.dbtx
import model.DoctorEntity
import model.NewTimeslot
import model.TimeslotEntity

@Target("Single doctor")
class DoctorUnit internal constructor(private val _doctor: DoctorEntity) {

	suspend fun listAllTimeslots() = dbtx {
		_doctor.timeslots
			.sortedWith(compareBy<TimeslotEntity> { it.date }
				.thenBy { it.time })
			.map { it.toTimeslot() }
	}

	suspend fun bindTimeslots(timeslotList: List<NewTimeslot>) = dbtx {
		val existingTimeslots = listAllTimeslots();

		timeslotList
			.filter {
				existingTimeslots.none { existing ->
					it.date == existing.date &&
						it.time == existing.time
				}
			}
			.map {
				TimeslotEntity.new {
					date = it.date
					time = it.time
					doctor = _doctor
				}
					.toTimeslot()
			}
	}

}
