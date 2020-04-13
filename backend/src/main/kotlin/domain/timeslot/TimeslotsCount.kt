package domain.timeslot

import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.count
import org.jetbrains.exposed.sql.selectAll
import server.DatabaseFactory.dbtx
import toDateTime
import java.time.LocalDateTime

object TimeslotsCount {

	suspend fun countAvailableTimeslots() = dbtx {
		val dateTime = LocalDateTime.now().toDateTime()

		TimeslotsTable.selectAll()
			.andWhere { TimeslotsTable.datetime greaterEq dateTime.value }
			.andWhere { TimeslotsTable.status eq TimeslotStatus.NEW.value }
			.count()
	}

	suspend fun stats() = dbtx {
		val count = TimeslotsTable.status.count().alias("count")

		TimeslotsTable
			.slice(TimeslotsTable.status, count)
			.selectAll()
			.groupBy(TimeslotsTable.status)
			.map {
				TimeslotStatusCount(
					TimeslotStatus.of(it[TimeslotsTable.status]),
					it[count]
				)
			}
	}

}
