package domain

import infra.DatabaseFactory.dbtx
import model.TimeslotEntity
import model.TimeslotsRepo
import org.jetbrains.exposed.sql.deleteAll

@Target("Set of all timeslots.")
object Timeslots {

	/**
	 * Deletes all time slots.
	 */
	suspend fun deleteAll() = dbtx {
		TimeslotsRepo.deleteAll();
	}

	/**
	 * Counts available timeslots in the future.
	 */
	fun countAvailableTimeslots() {
		TODO()
	}

	private fun findExisting(id: Int): TimeslotEntity {
		return TimeslotEntity.find { TimeslotsRepo.id eq id }.single()
	}

}
