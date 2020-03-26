package domain

import domain.DatabaseFactory.dbtx
import model.Doctor
import model.DoctorEntity

class DoctorUnit internal constructor(private val ref: Doctor) {

	suspend fun listTimeslots() = dbtx {
		DoctorEntity.findById(ref.id)?.timeslots
	}

}
