package domain.timeslot.verbs

import domain.timeslot.TimeslotId
import domain.timeslot.TimeslotStatus
import domain.timeslot.TimeslotsTable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.update

object ReserveTimeslotIfNew : (TimeslotId) -> Unit {
	override fun invoke(timeslotId: TimeslotId) {
		TimeslotsTable.update({
			TimeslotsTable.id eq timeslotId.value and
				(TimeslotsTable.status eq TimeslotStatus.NEW.value)
		}) {
			it[status] = TimeslotStatus.RESERVED.value
		}
	}
}
