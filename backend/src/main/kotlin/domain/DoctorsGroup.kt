package domain

import domain.DatabaseFactory.db
import model.Doctor
import model.DoctorEntity
import model.DoctorsRepo
import model.NewDoctor
import org.jetbrains.exposed.sql.deleteAll

class DoctorsGroup {

	suspend fun add(doctor: NewDoctor) : Doctor = db {
		val saved = DoctorEntity.new {
			name = doctor.name
			confirmed = false
			dateUpdated = System.currentTimeMillis()
		}
		findExisting(saved.id.value)
	}

	suspend fun findById(id: Int): Doctor? = db {
		DoctorEntity.findById(id)?.toDoctor()
	}

	/**
	 * Returns all doctors, ordered by ID (added time)
	 */
	suspend fun findAll(): List<Doctor> = db {
		DoctorEntity.all().toList().map { it.toDoctor() }
	}

	/**
	 * Deletes all doctors.
	 */
	fun deleteAll() {
		DoctorsRepo.deleteAll();
	}

	private fun findExisting(id: Int): Doctor {
		return DoctorEntity.find { DoctorsRepo.id eq id }.single().toDoctor()
	}

}