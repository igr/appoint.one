package domain.appointment.verbs

import domain.appointment.Appointment
import domain.appointment._FindAppointmentForTimeslot
import domain.doctor.DoctorsTable
import domain.doctor.toDoctor
import domain.timeslot.TimeslotId
import domain.timeslot.TimeslotsTable
import domain.timeslot.toTimeslot
import org.jetbrains.exposed.sql.select

object FindAppointmentForTimeslot : _FindAppointmentForTimeslot {
	override fun invoke(timeslotId: TimeslotId): Appointment? {
		return (TimeslotsTable innerJoin DoctorsTable)
			.select { TimeslotsTable.id eq timeslotId.value }
			.limit(5)
			.map {
				Appointment(it.toTimeslot(), it.toDoctor())
			}
			.singleOrNull()
	}
}
