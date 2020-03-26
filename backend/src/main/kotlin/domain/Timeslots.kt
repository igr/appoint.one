package domain

import domain.DatabaseFactory.dbtx
import model.TimeslotsRepo
import org.jetbrains.exposed.sql.deleteAll

object Timeslots {
	suspend fun deleteAll() = dbtx {
		TimeslotsRepo.deleteAll();
	}
}