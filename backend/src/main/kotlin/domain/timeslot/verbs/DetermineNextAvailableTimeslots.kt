package domain.timeslot.verbs

import DateTime
import domain.doctor.DoctorsTable
import domain.doctor.toDoctor
import domain.timeslot.TimeslotAndDoctor
import domain.timeslot.TimeslotStatus
import domain.timeslot.TimeslotsTable
import domain.timeslot.toTimeslot
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.select

object DetermineNextAvailableTimeslots : (Int, DateTime) -> List<TimeslotAndDoctor> {

	override fun invoke(minItemsCount: Int, from: DateTime): List<TimeslotAndDoctor> {
		val dateTimeNow = DateTime.now()
		val dateTimeEndInt = from.copy(day = from.day + 1).value

		val thisDayTimeSlots = (TimeslotsTable innerJoin DoctorsTable)
			.select { TimeslotsTable.status eq TimeslotStatus.NEW.value }
			.andWhere { TimeslotsTable.datetime greaterEq dateTimeNow.value }
			.andWhere { TimeslotsTable.datetime greaterEq from.value }
			.andWhere { TimeslotsTable.datetime less dateTimeEndInt }
			.orderBy(TimeslotsTable.datetime)
			.map {
				TimeslotAndDoctor(it.toTimeslot(), it.toDoctor())
			}
			.toMutableList()

		if (thisDayTimeSlots.size < minItemsCount) {
			val remainingCount = minItemsCount - thisDayTimeSlots.size

			val nextTimeslots = (TimeslotsTable innerJoin DoctorsTable)
				.select { TimeslotsTable.status eq TimeslotStatus.NEW.value }
				.andWhere { TimeslotsTable.datetime greaterEq dateTimeNow.value }
				.andWhere { TimeslotsTable.datetime greaterEq dateTimeEndInt }
				.orderBy(TimeslotsTable.datetime)
				.limit(remainingCount)
				.map {
					TimeslotAndDoctor(it.toTimeslot(), it.toDoctor())
				}
				.toList()

			thisDayTimeSlots.addAll(nextTimeslots)
		}

		return thisDayTimeSlots
	}

	val uptTo10 = { dateTime: DateTime -> DetermineNextAvailableTimeslots(10, dateTime) }
}
