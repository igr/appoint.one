package domain

import model.Country
import model.TimeslotEntity
import model.TimeslotsRepo
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.selectAll
import pair
import server.DatabaseFactory.dbtx
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
		val dt = LocalDateTime.now().pair()

		TimeslotsRepo.selectAll()
			.andWhere { TimeslotsRepo.date greaterEq dt.date }
			.andWhere { TimeslotsRepo.time greater dt.time }
			.count()
	}

	suspend fun findNextTimeslots(country: Country) = dbtx {
		_findNextTimeslots(country)
	}

	private fun findExisting(id: Int): TimeslotEntity {
		return TimeslotEntity.find { TimeslotsRepo.id eq id }.single()
	}

}
