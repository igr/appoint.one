package domain.timeslot

import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.update
import server.DatabaseFactory

class TimeslotStatusUpdater(private val timeslotId: Int) {

	suspend fun reserveIfNew() = DatabaseFactory.dbtx {
		TimeslotsTable.update({
			TimeslotsTable.id eq timeslotId and
				(TimeslotsTable.status eq TimeslotStatus.NEW.value)
		}) {
			it[status] = TimeslotStatus.RESERVED.value
		}
	}
}
