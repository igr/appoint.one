package domain

import model.Doctor
import model.DoctorEntity
import model.DoctorsRepo
import model.NewDoctor
import org.jetbrains.exposed.sql.deleteAll
import server.DatabaseFactory.dbtx

@TargetIs("Set of all doctors.")
object Doctors {

	suspend fun get(doctor: Doctor): DoctorUnit = dbtx {
		DoctorUnit(findExisting(doctor.id))
	}

	suspend fun get(doctorId: Int): DoctorUnit = dbtx {
		DoctorUnit(findExisting(doctorId))
	}

	suspend fun with(doctor: Doctor, consumer: suspend (du: DoctorUnit) -> Unit): Unit = dbtx {
		consumer(DoctorUnit(findExisting(doctor.id)))
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
		findExisting(saved.id.value).toDoctor()
	}

	/**
	 * Finds doctor by ID.
	 */
	suspend fun findById(id: Int): Doctor? = dbtx {
		DoctorEntity.findById(id)?.toDoctor()
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

	private fun findExisting(id: Int): DoctorEntity {
		return DoctorEntity.find { DoctorsRepo.id eq id }.single()
	}

}
