package domain.timeslot.verbs

import domain.timeslot.TimeslotId
import domain.timeslot.TimeslotsTable
import domain.user.AccessDenied
import domain.user.UserId
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.select

object AssertTimeslotIsOwnedByUser : (TimeslotId, UserId?) -> Unit {
	override fun invoke(timeslotId: TimeslotId, userId: UserId?) {
		if (userId == null) {
			throw AccessDenied()
		}
		// todo convert userid to doctorid, even its dummy
		val count = TimeslotsTable
			.slice(TimeslotsTable.id)
			.select { TimeslotsTable.id eq timeslotId.value }
			.andWhere { TimeslotsTable.doctorIdRef eq userId.value }
			.count()

		if (count != 1L) {
			throw AccessDenied()
		}
	}
}
