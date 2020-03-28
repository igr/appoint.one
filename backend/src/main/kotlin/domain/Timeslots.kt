package domain

import infra.DatabaseFactory.dbtx
import model.TimeslotEntity
import model.TimeslotsRepo
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.selectAll
import pair
import java.time.LocalDateTime

@Target("Set of all timeslots.")
object Timeslots {

	/**
	 * Deletes all time slots.
	 */
	suspend fun deleteAllTimeslots() = dbtx {
		TimeslotsRepo.deleteAll();
	}

	/**
	 * Counts available timeslots in the future.
	 */
	suspend fun countAvailableTimeslots() = dbtx {
		val dt = LocalDateTime.now().pair()

		TimeslotsRepo.selectAll()
			.andWhere { TimeslotsRepo.date greaterEq dt.date }
			.andWhere { TimeslotsRepo.time greater dt.time }
			.count()
	}

	private fun findExisting(id: Int): TimeslotEntity {
		return TimeslotEntity.find { TimeslotsRepo.id eq id }.single()
	}

}
