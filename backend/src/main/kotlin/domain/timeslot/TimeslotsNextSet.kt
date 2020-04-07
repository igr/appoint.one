package domain.timeslot

import DateTime
import domain.doctor.DoctorsTable
import domain.doctor.toDoctor
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.select
import server.DatabaseFactory.dbtx

/**
 * Finds next available timeslots.
 */
object TimeslotsNextSet {

	suspend fun get(limit: Int = 5): List<TimeslotAndDoctor> = dbtx {
		val dateTimeInt = DateTime.now().value

		(TimeslotsTable innerJoin DoctorsTable)
			.select { TimeslotsTable.status eq TimeslotStatus.NEW.value }
			.andWhere { TimeslotsTable.datetime greaterEq dateTimeInt }
			.orderBy(TimeslotsTable.datetime)
			.limit(limit)
			.map {
				TimeslotAndDoctor(it.toTimeslot(), it.toDoctor())
			}
			.toList()
	}
}
