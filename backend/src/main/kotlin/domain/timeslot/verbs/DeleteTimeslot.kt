package domain.timeslot.verbs

import domain.timeslot.TimeslotId
import domain.timeslot.TimeslotsTable
import org.jetbrains.exposed.sql.deleteWhere

object DeleteTimeslot : (TimeslotId) -> Unit {
	override fun invoke(timeslotId: TimeslotId) {
		TimeslotsTable
			.deleteWhere { TimeslotsTable.id eq timeslotId.value }
	}

}
