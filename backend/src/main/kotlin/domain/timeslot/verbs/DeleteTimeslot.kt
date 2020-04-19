package domain.timeslot.verbs

import domain.timeslot.TimeslotId
import domain.timeslot.TimeslotsTable
import domain.timeslot._DeleteTimeslot
import org.jetbrains.exposed.sql.deleteWhere

object DeleteTimeslot : _DeleteTimeslot {
	override fun invoke(timeslotId: TimeslotId) {
		TimeslotsTable
			.deleteWhere { TimeslotsTable.id eq timeslotId.value }
	}

}
