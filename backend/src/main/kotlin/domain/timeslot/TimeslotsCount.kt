package domain.timeslot

import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.selectAll
import server.DatabaseFactory.dbtx
import toDateTime
import java.time.LocalDateTime

object TimeslotsCount {

	suspend fun availableTimeslots() = dbtx {
		val dateTime = LocalDateTime.now().toDateTime()

		TimeslotsTable.selectAll()
			.andWhere { TimeslotsTable.datetime greaterEq dateTime.value }
			.andWhere { TimeslotsTable.status eq TimeslotStatus.NEW.value }
			.count()
	}

}
