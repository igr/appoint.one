package domain.timeslot

import DateTime
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import domain.Id
import domain.doctor.Doctor
import domain.doctor.DoctorId
import org.jetbrains.exposed.dao.id.EntityID
import com.fasterxml.jackson.annotation.JsonCreator.Mode.DELEGATING as m

data class TimeslotId @JsonCreator(mode = JsonCreator.Mode.DELEGATING) constructor(
	@JsonValue override val value: Int
) : Id()

fun Int.toTimeslotId(): TimeslotId {
	return TimeslotId(this)
}

fun String.toTimeslotIt(): TimeslotId {
	return TimeslotId(this.toInt())
}

fun EntityID<Int>.toTimeslotId(): TimeslotId {
	return TimeslotId(this.value);
}

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
	val id: TimeslotId,
	val status: TimeslotStatus,
	val datetime: DateTime,
	val doctorId: DoctorId
)

data class TimeslotAndDoctor(
	val timeslot: Timeslot,
	val doctor: Doctor
)

data class NewTimeslot(
	val datetime: DateTime,
	val doctorId: DoctorId
)
