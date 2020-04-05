package domain

import model.Country
import model.TimeslotAndDoctor
import model.TimeslotStatus
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.select
import repo.DoctorsTable
import repo.TimeslotsTable
import repo.toDoctor
import repo.toTimeslot
import toDateTime
import java.time.LocalDateTime

/**
 * Finds next available timeslots for given inquiry.
 */
fun _findNextTimeslots(country: Country): List<TimeslotAndDoctor> {

	val dateTimeInt = LocalDateTime.now().toDateTime().value

/*
	This query has N+1 problem.

	return TimeslotsRepo.innerJoin(DoctorsRepo)
		.select { TimeslotsRepo.doctor eq DoctorsRepo.id }
		.andWhere { TimeslotsRepo.date greaterEq dt.date }
		.andWhere { TimeslotsRepo.time greater dt.time }
		.andWhere { DoctorsRepo.country eq country.value }
		.orderBy(TimeslotsRepo.date)
		.orderBy(TimeslotsRepo.time)
		.limit(5)
		.let { TimeslotEntity.wrapRows(it) }
		.map { it.toTimeslot() }
		.toList()
*/

	return TimeslotsTable.innerJoin(DoctorsTable)
		.select { TimeslotsTable.doctorId eq DoctorsTable.id }
		.andWhere { TimeslotsTable.status eq TimeslotStatus.NEW.value }
		.andWhere { TimeslotsTable.datetime greaterEq dateTimeInt }
		.andWhere { DoctorsTable.country eq country.id }
		.orderBy(TimeslotsTable.datetime)
		.limit(5)
		.map {
			TimeslotAndDoctor(it.toTimeslot(), it.toDoctor())
		}
		.toList()

}
