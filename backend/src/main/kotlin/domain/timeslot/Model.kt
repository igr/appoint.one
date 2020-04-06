package domain.timeslot

import DateTime
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import domain.doctor.Doctor
import com.fasterxml.jackson.annotation.JsonCreator.Mode.DELEGATING as m

enum class TimeslotStatus @JsonCreator(mode = m) constructor(@JsonValue val value: Int) {
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
