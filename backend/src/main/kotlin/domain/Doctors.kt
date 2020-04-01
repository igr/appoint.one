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
	suspend fun addNewDoctor(doctor: NewDoctorWithUser): Doctor = dbtx {
		val user = UserEntity.findById(doctor.userId)!!
		DoctorEntity.add(doctor.data, user).toDoctor()
	}

	suspend fun addNewDoctor(payload: NewDoctorAndUser): Doctor = dbtx {
		val user = UserEntity.add(payload.user)
		DoctorEntity.add(payload.doctor, user).toDoctor()
	}

	/**
	 * Finds existing doctor by ID.
	 */
	suspend fun findById(id: DoctorId): Doctor? = dbtx {
		DoctorEntity.findById(id)?.toDoctor()
	}

	/**
	 * Returns doctor by its user id.
	 */
	suspend fun findByUserId(userId: UserId): Doctor? = dbtx {
		DoctorEntity
			.find { DoctorsRepo.userRef eq userId.value }
			.firstOrNull()?.toDoctor()
	}

	/**
	 * Returns all doctors, ordered by ID (added time)
	 */
	suspend fun listAllDoctors(): List<Doctor> = dbtx {
		DoctorEntity.all().sortedBy { it.id }.toList().map { it.toDoctor() }
	}

	/**
	 * Deletes all doctors.
	 */
	suspend fun deleteAllDoctors() = dbtx {
		DoctorsRepo.deleteAll();
	}
}
