package domain.appointment

import domain.timeslot.TimeslotId

typealias _FindAppointmentForTimeslot = (TimeslotId) -> Appointment?
