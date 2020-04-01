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

data class TimeslotId(
	override val value: Int
) : Id()

typealias NewTimeslot = DateTime

data class Timeslot(
	val id: TimeslotId,
	val status: TimeslotStatus,
	val datetime: DateTime,
	val doctor: Doctor
)

data class NewDoctorTimeslots(
	val doctorId: DoctorId,
	val timeslots: List<NewTimeslot>
)
