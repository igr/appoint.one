package domain

import infra.DatabaseFactory.dbtx
import model.TimeslotEntity
import model.TimeslotsRepo
import org.jetbrains.exposed.sql.*
import java.time.LocalDateTime


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
	fun countAvailableTimeslots(): Long {
		val currentDateTime = LocalDateTime.now()
		val date = currentDateTime.year * 10000 + currentDateTime.monthValue * 100 + currentDateTime.dayOfMonth
		val time = currentDateTime.hour * 100 + currentDateTime.minute

		val query = TimeslotsRepo.selectAll()
		query.andWhere { TimeslotsRepo.date eq date }
		query.andWhere { TimeslotsRepo.time greater time }
		return query.count()
	}

	private fun findExisting(id: Int): TimeslotEntity {
		return TimeslotEntity.find { TimeslotsRepo.id eq id }.single()
	}

}
