package model

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object TimeslotsRepo : IntIdTable(name = "timeslots") {
	val date = integer("date").index()
	val time = integer("time").index()
	val doctor = reference("doctor_id", DoctorsRepo.id)
}

class TimeslotEntity(id: EntityID<Int>) : Entity<Int>(id) {
	companion object : EntityClass<Int, TimeslotEntity>(TimeslotsRepo)

	var date by TimeslotsRepo.date
	var time by TimeslotsRepo.time
	var doctor by DoctorEntity referencedOn TimeslotsRepo.doctor

	fun toTimeslot(): Timeslot {
		return Timeslot(
			date = date,
			time = time,
			doctor = doctor.toDoctor()
		)
	}
}

data class NewTimeslot(
	val date: Int,
	val time: Int
)

data class Timeslot(
	val date: Int,
	val time: Int,
	val doctor: Doctor
)
