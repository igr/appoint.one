package domain.timeslot.verbs

import domain.timeslot.TimeslotStatus
import domain.timeslot.TimeslotsTable
import id.TimeslotId
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.update

object ActivateTimeslotIfReserved : (TimeslotId) -> Unit {
	override fun invoke(timeslotId: TimeslotId) {
		TimeslotsTable.update({
			TimeslotsTable.id eq timeslotId.value and
				(TimeslotsTable.status eq TimeslotStatus.RESERVED.value)
		}) {
			it[status] = TimeslotStatus.NEW.value
		}
	}
}
