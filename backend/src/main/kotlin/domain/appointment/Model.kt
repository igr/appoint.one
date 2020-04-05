package domain.appointment

import domain.doctor.Doctor
import domain.timeslot.Timeslot

data class Appointment(
	val timeslot: Timeslot,
	val doctor: Doctor
)
