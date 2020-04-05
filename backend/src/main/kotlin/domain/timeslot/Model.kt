package domain.timeslot

import DateTime
import domain.doctor.Doctor

enum class TimeslotStatus(val value: Int) {
	NEW(0),
	RESERVED(10),
	CANCELED(20),
	DONE(30);

	companion object {
		fun of(id: Int): TimeslotStatus = values().find { it.value == id }!!
	}
}

data class Timeslot(
	val id: Int,
	val status: TimeslotStatus,
	val datetime: DateTime,
	val doctorId: Int
)

data class TimeslotAndDoctor(
	val timeslot: Timeslot,
	val doctor: Doctor
)

data class NewTimeslot(
	val datetime: DateTime,
	val doctorId: Int
)
