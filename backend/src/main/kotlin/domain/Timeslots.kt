package domain

import model.Country
import model.TimeslotEntity
import model.TimeslotsRepo
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.selectAll
import server.DatabaseFactory.dbtx
import toDateTimeLong
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
		val dateTimeInt = LocalDateTime.now().toDateTimeLong()

		TimeslotsRepo.selectAll()
			.andWhere { TimeslotsRepo.datetime greaterEq dateTimeInt }
			.count()
	}

	suspend fun findNextTimeslots(country: Country) = dbtx {
		_findNextTimeslots(country)
	}

	private fun findExisting(id: Int): TimeslotEntity {
		return TimeslotEntity.find { TimeslotsRepo.id eq id }.single()
	}

}
