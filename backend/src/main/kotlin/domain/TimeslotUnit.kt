package domain

import model.TimeslotStatus
import org.jetbrains.exposed.sql.update
import repo.TimeslotsTable
import server.DatabaseFactory

class TimeslotUnit internal constructor(private val timeslotId: Int) {

	suspend fun reserve() = DatabaseFactory.dbtx {
		TimeslotsTable.update({
			TimeslotsTable.id eq timeslotId
			TimeslotsTable.status neq TimeslotStatus.NEW.value
		}) {
			it[status] = TimeslotStatus.RESERVED.value
		}
	}

}

