package domain.doctor.verbs

import domain.doctor.DoctorId
import domain.doctor._ListDoctorsTimeslots
import domain.timeslot.Timeslot
import domain.timeslot.TimeslotsTable
import domain.timeslot.toTimeslot
import org.jetbrains.exposed.sql.select

object ListDoctorsTimeslots : _ListDoctorsTimeslots {

	override fun invoke(doctorId: DoctorId): List<Timeslot> {
		return TimeslotsTable.select {
			TimeslotsTable.doctorIdRef eq doctorId.value
		}
			.sortedWith(compareBy { it[TimeslotsTable.datetime] })
			.reversed()
			.map { it.toTimeslot() }
	}
}
