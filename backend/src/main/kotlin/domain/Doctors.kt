package domain

import domain.DatabaseFactory.dbtx
import model.Doctor
import model.DoctorEntity
import model.DoctorsRepo
import model.NewDoctor
import org.jetbrains.exposed.sql.deleteAll

object Doctors {

	suspend fun with(doctor: Doctor): DoctorUnit = dbtx {
		DoctorUnit(findExisting(doctor.id))
	}

	suspend fun with(doctor: Doctor, consumer: suspend (du: DoctorUnit) -> Unit): Unit = dbtx {
		consumer(DoctorUnit(findExisting(doctor.id)))
	}

	suspend fun add(doctor: NewDoctor): Doctor = dbtx {
		val saved = DoctorEntity.new {
			name = doctor.name
			confirmed = false
			dateUpdated = System.currentTimeMillis()
		}
		findExisting(saved.id.value).toDoctor()
	}

	suspend fun findById(id: Int): Doctor? = dbtx {
		DoctorEntity.findById(id)?.toDoctor()
	}

	/**
	 * Returns all doctors, ordered by ID (added time)
	 */
	suspend fun findAll(): List<Doctor> = dbtx {
		DoctorEntity.all().sortedBy { it.id }.toList().map { it.toDoctor() }
	}

	/**
	 * Deletes all doctors.
	 */
	suspend fun deleteAll() = dbtx {
		DoctorsRepo.deleteAll();
	}

	private fun findExisting(id: Int): DoctorEntity {
		return DoctorEntity.find { DoctorsRepo.id eq id }.single()
	}

}