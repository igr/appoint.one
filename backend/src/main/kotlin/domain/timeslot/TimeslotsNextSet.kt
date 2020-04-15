package domain.timeslot

import DateTime
import domain.doctor.DoctorsTable
import domain.doctor.toDoctor
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.select
import server.DatabaseFactory.dbtx

/**
 * Finds next available timeslots.
 */
class TimeslotsNextSet(private val minItems: Int = 10) {

	suspend fun get(): List<TimeslotAndDoctor> = dbtx {
		from(DateTime.now())
	}

	suspend fun from(from: DateTime): List<TimeslotAndDoctor> = dbtx {
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

		if (thisDayTimeSlots.size < minItems) {
			val remainingCount = minItems - thisDayTimeSlots.size

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

		thisDayTimeSlots
	}
}
