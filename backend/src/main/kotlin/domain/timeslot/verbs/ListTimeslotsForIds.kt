package domain.timeslot.verbs

import domain.timeslot.*
import org.jetbrains.exposed.sql.select

object ListTimeslotsForIds : _ListTimeslotsForIds {
	override fun invoke(timeslots: List<TimeslotId>): List<Timeslot> {
		return TimeslotsTable
			.select { TimeslotsTable.id inList timeslots.map { it.value } }
			.map { it.toTimeslot() }
	}
}
