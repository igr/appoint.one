package domain.timeslot.verbs

import domain.timeslot.*
import org.jetbrains.exposed.sql.select

object FindTimeslotById : _FindTimeslotById {
	override fun invoke(timeslotId: TimeslotId): Timeslot? {
		return TimeslotsTable
			.select { TimeslotsTable.id eq timeslotId.value }
			.singleOrNull()
			?.toTimeslot()
	}
}
