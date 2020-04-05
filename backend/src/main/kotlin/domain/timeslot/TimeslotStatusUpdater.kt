package domain.timeslot

import org.jetbrains.exposed.sql.update
import server.DatabaseFactory

class TimeslotStatusUpdater(private val timeslotId: Int) {

	suspend fun reserveIfNew() = DatabaseFactory.dbtx {
		TimeslotsTable.update({
			TimeslotsTable.id eq timeslotId
			TimeslotsTable.status neq TimeslotStatus.NEW.value
		}) {
			it[status] = TimeslotStatus.RESERVED.value
		}
	}
}
