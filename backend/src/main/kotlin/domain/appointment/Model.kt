package domain.appointment

import domain.doctor.Doctor
import domain.timeslot.Timeslot
import kotlinx.serialization.Serializable

@Serializable
data class Appointment(
	val timeslot: Timeslot,
	val doctor: Doctor
)
