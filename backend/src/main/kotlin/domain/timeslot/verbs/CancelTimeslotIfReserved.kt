package domain.timeslot.verbs

import domain.timeslot.TimeslotId
import domain.timeslot.TimeslotStatus
import domain.timeslot.TimeslotsTable
import domain.timeslot._CancelTimeslotIfReserved
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.update

object CancelTimeslotIfReserved : _CancelTimeslotIfReserved {
	override fun invoke(timeslotId: TimeslotId) {
		TimeslotsTable.update({
			TimeslotsTable.id eq timeslotId.value and
				(TimeslotsTable.status eq TimeslotStatus.RESERVED.value)
		}) {
			it[status] = TimeslotStatus.CANCELED.value
		}
	}
}
