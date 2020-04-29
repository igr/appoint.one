package domain.doctor.verbs

import domain.DateTime
import domain.timeslot.NewTimeslot
import domain.timeslot.TimeslotsTable
import domain.timeslot.data
import id.DoctorId
import id.TimeslotId
import id.toTimeslotId
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
