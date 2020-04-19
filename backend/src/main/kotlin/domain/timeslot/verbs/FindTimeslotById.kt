package domain.timeslot.verbs

import domain.timeslot.Timeslot
import domain.timeslot.TimeslotId
import domain.timeslot.TimeslotsTable
import domain.timeslot.toTimeslot
import org.jetbrains.exposed.sql.select

object FindTimeslotById : (TimeslotId) -> Timeslot? {
	override fun invoke(timeslotId: TimeslotId): Timeslot? {
		return TimeslotsTable
			.select { TimeslotsTable.id eq timeslotId.value }
			.singleOrNull()
			?.toTimeslot()
	}
}
