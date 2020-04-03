package domain

import model.*
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.selectAll
import server.DatabaseFactory.dbtx
import toDateTime
import java.time.LocalDateTime

@TargetIs("Set of all timeslots.")
object Timeslots {

	suspend fun with(timeslotId: Int): TimeslotUnit = dbtx {
		TimeslotUnit(TimeslotEntity.findExisting(timeslotId))
	}

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
			.andWhere { TimeslotsRepo.status eq TimeslotStatus.NEW.value }
			.count()
	}

	suspend fun findNextTimeslots(country: Country) = dbtx {
		_findNextTimeslots(country)
	}

	suspend fun findById(id: Int): Timeslot? = dbtx {
		TimeslotEntity.findById(id)?.toTimeslot()
	}

	suspend fun deleteById(id: Int) = dbtx {
		TimeslotsRepo.deleteWhere { TimeslotsRepo.id eq id }
	}

}
