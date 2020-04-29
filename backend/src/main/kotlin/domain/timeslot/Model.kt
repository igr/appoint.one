package domain.timeslot

import appoint1.annotations.GENERATED
import appoint1.annotations.IdGen
import domain.DateTime
import domain.Id
import domain.doctor.Doctor
import id.DoctorId
import id.TimeslotId
import kotlinx.serialization.Serializable

@IdGen
val _TimeslotId: Id = GENERATED()

@Serializable(with = TimeslotStatusSerializer::class)
enum class TimeslotStatus(val value: Int) {
	NEW(0),
	RESERVED(10),
	CANCELED(20),
	DONE(30);

	companion object {
		fun of(id: Int): TimeslotStatus = values().find { it.value == id }!!
	}
}

@Serializable
data class Timeslot(
	val id: TimeslotId,
	val status: TimeslotStatus,
	val datetime: DateTime,
	val doctorId: DoctorId
)

@Serializable
data class TimeslotAndDoctor(
	val timeslot: Timeslot,
	val doctor: Doctor
)

@Serializable
data class NewTimeslot(
	val datetime: DateTime,
	val doctorId: DoctorId
)

@Serializable
data class TimeslotStatusCount(
	val status: TimeslotStatus,
	val count: Long
)
