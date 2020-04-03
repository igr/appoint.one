package model

import DateTime
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object TimeslotsRepo : IntIdTable(name = "timeslots") {
	val status = integer("status").index()
	val datetime = long("datetime").index()
	val doctoreRef = reference("doctor_id", DoctorsRepo.id)
}

class TimeslotEntity(id: EntityID<Int>) : Entity<Int>(id) {
	companion object : EntityClass<Int, TimeslotEntity>(TimeslotsRepo)

	var status by TimeslotsRepo.status
	var datetime by TimeslotsRepo.datetime
	var doctorRef by DoctorEntity referencedOn TimeslotsRepo.doctoreRef

	fun toTimeslot(): Timeslot {
		return Timeslot(
			id = id.value,
			status = TimeslotStatus.of(status),
			datetime = DateTime(datetime),
			doctor = doctorRef.toDoctor()
		)
	}

	fun toTimeslot(doc: DoctorEntity): Timeslot {
		assert(doctorRef.id == doc.id)

		return Timeslot(
			id = id.value,
			status = TimeslotStatus.of(status),
			datetime = DateTime(datetime),
			doctor = doc.toDoctor()
		)
	}
}

fun TimeslotEntity.Companion.findExisting(id: Int): TimeslotEntity {
	return findById(id)!!
}

fun TimeslotEntity.Companion.add(dateTime: DateTime, doc: DoctorEntity): TimeslotEntity {
	return TimeslotEntity.new {
		status = TimeslotStatus.NEW.value
		datetime = dateTime.value
		doctorRef = doc
	}
}
