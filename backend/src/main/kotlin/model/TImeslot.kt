package model

import DateTime
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object TimeslotsRepo : IntIdTable(name = "timeslots") {
	val datetime = long("datetime").index()
	val doctor = reference("doctor_id", DoctorsRepo.id)
}

class TimeslotEntity(id: EntityID<Int>) : Entity<Int>(id) {
	companion object : EntityClass<Int, TimeslotEntity>(TimeslotsRepo)

	var datetime by TimeslotsRepo.datetime
	var doctor by DoctorEntity referencedOn TimeslotsRepo.doctor

	fun toTimeslot(): Timeslot {
		return Timeslot(
			datetime = DateTime(datetime),
			doctor = doctor.toDoctor()
		)
	}

	fun toTimeslot(doc: DoctorEntity): Timeslot {
		assert(doctor.id == doc.id)

		return Timeslot(
			datetime = DateTime(datetime),
			doctor = doc.toDoctor()
		)
	}
}

fun TimeslotEntity.Companion.add(timeslot: NewTimeslot, doc: DoctorEntity): TimeslotEntity {
	return TimeslotEntity.new {
		datetime = timeslot.value
		doctor = doc
	}
}

typealias NewTimeslot = DateTime

data class Timeslot(
	val datetime: DateTime,
	val doctor: Doctor
)

data class NewDoctorTimeslots(
	val doctorId: DoctorId,
	val timeslots: List<NewTimeslot>
)
