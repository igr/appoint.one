package model

import DateTime

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
	val doctor: Doctor
)

data class NewDoctorTimeslot(
	val doctorId: Int,
	val datetime: DateTime
)
