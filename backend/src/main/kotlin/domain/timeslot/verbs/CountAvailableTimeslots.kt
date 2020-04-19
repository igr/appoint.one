package domain.timeslot.verbs

import domain.timeslot.TimeslotStatus
import domain.timeslot.TimeslotsTable
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.selectAll
import toDateTime
import java.time.LocalDateTime

object CountAvailableTimeslots : () -> Long {
	override fun invoke(): Long {
		val dateTime = LocalDateTime.now().toDateTime()

		return TimeslotsTable.selectAll()
			.andWhere { TimeslotsTable.datetime greaterEq dateTime.value }
			.andWhere { TimeslotsTable.status eq TimeslotStatus.NEW.value }
			.count()
	}
}
