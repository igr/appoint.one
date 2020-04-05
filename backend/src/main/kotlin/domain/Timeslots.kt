package domain

import model.Country
import model.Timeslot
import model.TimeslotStatus
import org.jetbrains.exposed.sql.*
import repo.TimeslotsTable
import repo.toTimeslot
import server.DatabaseFactory.dbtx
import toDateTime
import java.time.LocalDateTime

object Timeslots {

	suspend fun with(timeslotId: Int): TimeslotUnit = dbtx {
		TimeslotUnit(timeslotId)
	}

	/**
	 * Deletes all time slots.
	 */
	suspend fun deleteAllTimeslots() = dbtx {
		TimeslotsTable.deleteAll();
	}

	/**
	 * Counts ALL available timeslots in the future.
	 */
	suspend fun countAvailableTimeslots() = dbtx {
		val dateTime = LocalDateTime.now().toDateTime()

		TimeslotsTable.selectAll()
			.andWhere { TimeslotsTable.datetime greaterEq dateTime.value }
			.andWhere { TimeslotsTable.status eq TimeslotStatus.NEW.value }
			.count()
	}

	suspend fun findNextTimeslots(country: Country) = dbtx {
		_findNextTimeslots(country)
	}

	suspend fun findById(id: Int): Timeslot? = dbtx {
		TimeslotsTable.select { TimeslotsTable.id eq id }.singleOrNull()?.toTimeslot()
	}

	suspend fun deleteById(id: Int) = dbtx {
		TimeslotsTable.deleteWhere { TimeslotsTable.id eq id }
	}

}
