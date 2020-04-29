package domain.doctor.verbs

import domain.timeslot.Timeslot
import domain.timeslot.TimeslotsTable
import domain.timeslot.toTimeslot
import id.DoctorId
import org.jetbrains.exposed.sql.select

object ListDoctorsTimeslots : (DoctorId) -> List<Timeslot> {

	override fun invoke(doctorId: DoctorId): List<Timeslot> {
		return TimeslotsTable.select {
			TimeslotsTable.doctorIdRef eq doctorId.value
		}
			.sortedWith(compareBy { it[TimeslotsTable.datetime] })
			.reversed()
			.map { it.toTimeslot() }
	}
}
