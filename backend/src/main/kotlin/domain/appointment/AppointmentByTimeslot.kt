package domain.appointment

import domain.doctor.DoctorsTable
import domain.doctor.toDoctor
import domain.timeslot.TimeslotStatus
import domain.timeslot.TimeslotsTable
import domain.timeslot.toTimeslot
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.select
import server.DatabaseFactory.dbtx

class AppointmentByTimeslot(private val timeslotId: Int) {

	suspend fun getReserved(): Appointment? = dbtx {
		(TimeslotsTable innerJoin DoctorsTable)
			.select { TimeslotsTable.id eq timeslotId }
			.andWhere { TimeslotsTable.status eq TimeslotStatus.RESERVED.value }
			.limit(5)
			.map {
				Appointment(it.toTimeslot(), it.toDoctor())
			}
			.singleOrNull()
	}
}
