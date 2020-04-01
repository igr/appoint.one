package domain

import model.*
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.select
import toDateTime
import java.time.LocalDateTime

/**
 * Finds next available timeslots for given inquiry.
 */
fun _findNextTimeslots(country: Country): List<Timeslot> {

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
	return TimeslotsRepo.innerJoin(DoctorsRepo)
		.select { TimeslotsRepo.doctor eq DoctorsRepo.id }
		.andWhere { TimeslotsRepo.datetime greaterEq dateTimeInt }
		.andWhere { DoctorsRepo.country eq country.value }
		.orderBy(TimeslotsRepo.datetime)
		.limit(5)
		.map {
			val de = DoctorEntity.wrapRow(it)
			TimeslotEntity.wrapRow(it).toTimeslot(de)
		}
		.toList()

}
