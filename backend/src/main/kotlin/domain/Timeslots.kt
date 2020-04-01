package domain

import model.Country
import model.TimeslotsRepo
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.selectAll
import server.DatabaseFactory.dbtx
import toDateTime
import java.time.LocalDateTime

@TargetIs("Set of all timeslots.")
object Timeslots {

	/**
	 * Deletes all time slots.
	 */
	suspend fun deleteAllTimeslots() = dbtx {
		TimeslotsRepo.deleteAll();
	}

	/**
	 * Counts ALL available timeslots in the future.
	 */
	suspend fun countAvailableTimeslots() = dbtx {
		val dateTime = LocalDateTime.now().toDateTime()

		TimeslotsRepo.selectAll()
			.andWhere { TimeslotsRepo.datetime greaterEq dateTime.value }
			.count()
	}

	suspend fun findNextTimeslots(country: Country) = dbtx {
		_findNextTimeslots(country)
	}

}
