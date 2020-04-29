package domain.timeslot.verbs

import domain.timeslot.Timeslot
import domain.timeslot.TimeslotsTable
import domain.timeslot.toTimeslot
import id.TimeslotId
import org.jetbrains.exposed.sql.select

object FindExistingTimeslotById : (TimeslotId) -> Timeslot {
	override fun invoke(timeslotId: TimeslotId): Timeslot {
		return TimeslotsTable
			.select { TimeslotsTable.id eq timeslotId.value }
			.single()
			.toTimeslot()
	}
}
