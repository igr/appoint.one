package model

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object TimeslotsRepo : IntIdTable(name = "timeslots") {
	val date = integer("date")
	val time = integer("time")
	val doctor = integer("doctor_id").references(DoctorsRepo.id)
}

class TimeslotEntity(id: EntityID<Int>) : Entity<Int>(id) {
	companion object : EntityClass<Int, TimeslotEntity>(TimeslotsRepo)

	var date by TimeslotsRepo.date
	var time by TimeslotsRepo.time
	var doctor by DoctorEntity referencedOn TimeslotsRepo.doctor
}
