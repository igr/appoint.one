package domain.timeslot.verbs

import domain.timeslot.Timeslot
import domain.timeslot.TimeslotsTable
import domain.timeslot.toTimeslot
import id.TimeslotId
import org.jetbrains.exposed.sql.select

object ListTimeslotsForIds : (List<TimeslotId>) -> List<Timeslot> {
	override fun invoke(timeslots: List<TimeslotId>): List<Timeslot> {
		return TimeslotsTable
			.select { TimeslotsTable.id inList timeslots.map { it.value } }
			.map { it.toTimeslot() }
	}
}
