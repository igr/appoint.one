package domain

import model.Country
import model.DoctorsRepo
import model.TimeslotEntity
import model.TimeslotsRepo
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.select
import pair
import java.time.LocalDateTime

/**
 * Finds next available timeslots for given inquiry.
 */
fun _findNextTimeslots(country: Country): List<TimeslotEntity> {

	val dt = LocalDateTime.now().pair()

	return TimeslotsRepo.innerJoin(DoctorsRepo)
		.select { TimeslotsRepo.doctor eq DoctorsRepo.id }
		.andWhere { TimeslotsRepo.date greaterEq dt.date }
		.andWhere { TimeslotsRepo.time greater dt.time }
		.andWhere { DoctorsRepo.country eq country.value }
		.orderBy(TimeslotsRepo.date)
		.orderBy(TimeslotsRepo.time)
		.limit(5)
		.let { TimeslotEntity.wrapRows(it) }
		.toList()

//	TimeslotsRepo.selectAll()
//		.andWhere { TimeslotsRepo.date greaterEq dt.date }
//		.andWhere { TimeslotsRepo.time greater dt.time }
//		.andWhere { TimeslotsRepo.doctor.country eq country }
//		.count()
}
