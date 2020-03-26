package domain

import infra.DatabaseFactory.dbtx
import model.*
import org.jetbrains.exposed.sql.deleteAll

object Timeslots {
	suspend fun deleteAll() = dbtx {
		TimeslotsRepo.deleteAll();
	}

	suspend fun add(timeslot: NewTimeslot): Timeslot = dbtx {
		val saved = TimeslotEntity.new {
			date = timeslot.date
			time = timeslot.time
		}
		Timeslots.findExisting(saved.id.value).toTimeslot()
	}

	private fun findExisting(id: Int): TimeslotEntity {
		return TimeslotEntity.find { TimeslotsRepo.id eq id }.single()
	}

}
