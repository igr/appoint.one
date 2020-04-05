package domain.timeslot

import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import server.DatabaseFactory

class TimeslotById(private val id: Int) {

	suspend fun get(): Timeslot? = DatabaseFactory.dbtx {
		TimeslotsTable.select { TimeslotsTable.id eq id }.singleOrNull()?.toTimeslot()
	}

	suspend fun delete() = DatabaseFactory.dbtx {
		TimeslotsTable.deleteWhere { TimeslotsTable.id eq id }
	}

}
