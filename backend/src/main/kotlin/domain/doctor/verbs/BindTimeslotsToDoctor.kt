package domain.doctor.verbs

import DateTime
import domain.doctor.DoctorId
import domain.timeslot.*
import org.jetbrains.exposed.sql.insertAndGetId

object BindTimeslotsToDoctor : (DoctorId, List<DateTime>) -> List<TimeslotId> {
	override fun invoke(doctorId: DoctorId, dateTimeList: List<DateTime>): List<TimeslotId> {
		val existingTimeslots = ListDoctorsTimeslots(doctorId)

		return dateTimeList
			.filter {
				existingTimeslots.none { existing ->
					existing.datetime == it
				}
			}
			.map {
				NewTimeslot(datetime = it, doctorId = doctorId)
			}
			.map { newTimeslot ->
				TimeslotsTable.insertAndGetId {
					newTimeslot.data(it)
				}.toTimeslotId()
			}
	}
}
