package domain.timeslot.verbs

import domain.timeslot.TimeslotsTable
import id.TimeslotId
import org.jetbrains.exposed.sql.deleteWhere

object DeleteTimeslot : (TimeslotId) -> Unit {
	override fun invoke(timeslotId: TimeslotId) {
		TimeslotsTable
			.deleteWhere { TimeslotsTable.id eq timeslotId.value }
	}

}
