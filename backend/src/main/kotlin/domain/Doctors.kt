package domain

import model.*
import org.jetbrains.exposed.sql.deleteAll
import server.DatabaseFactory.dbtx

@TargetIs("Set of all doctors.")
object Doctors {

	suspend fun with(doctor: Doctor): DoctorUnit = dbtx {
		DoctorUnit(DoctorEntity.findExisting(doctor.id.value))
	}

	suspend fun with(doctorId: DoctorId): DoctorUnit = dbtx {
		DoctorUnit(DoctorEntity.findExisting(doctorId.value))
	}

	suspend fun with(doctor: Doctor, consumer: suspend (du: DoctorUnit) -> Unit): Unit = dbtx {
		consumer(DoctorUnit(DoctorEntity.findExisting(doctor.id.value)))
	}

	/**
	 * Adds new doctor.
	 */
	suspend fun addNewDoctor(doctor: NewDoctor): Doctor = dbtx {
		val saved = DoctorEntity.new {
			name = doctor.name
			confirmed = false
			country = doctor.country.value
			dateUpdated = System.currentTimeMillis()
		}
		DoctorEntity.findExisting(saved.id.value).toDoctor()
	}

	/**
	 * Finds doctor by ID.
	 */
	suspend fun findById(id: DoctorId): Doctor? = dbtx {
		DoctorEntity.findById(id.value)?.toDoctor()
	}

	/**
	 * Returns all doctors, ordered by ID (added time)
	 */
	suspend fun findAllDoctors(): List<Doctor> = dbtx {
		DoctorEntity.all().sortedBy { it.id }.toList().map { it.toDoctor() }
	}

	/**
	 * Deletes all doctors.
	 */
	suspend fun deleteAllDoctors() = dbtx {
		DoctorsRepo.deleteAll();
	}
}
