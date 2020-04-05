package domain.timeslot

import DateTime
import domain.country.Country
import domain.doctor.DoctorsTable
import domain.doctor.toDoctor
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.select
import server.DatabaseFactory.dbtx

/**
 * Finds next available timeslots.
 */
object TimeslotsNextSet {

	suspend fun byCountry(country: Country): List<TimeslotAndDoctor> = dbtx {
		val dateTimeInt = DateTime.now().value

		(TimeslotsTable innerJoin DoctorsTable)
			.select { TimeslotsTable.status eq TimeslotStatus.NEW.value }
			.andWhere { TimeslotsTable.datetime greaterEq dateTimeInt }
			.orderBy(TimeslotsTable.datetime)
			.limit(5)
			.map {
				TimeslotAndDoctor(it.toTimeslot(), it.toDoctor())
			}
			.toList()
	}
}
