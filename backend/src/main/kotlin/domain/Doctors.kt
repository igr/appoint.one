package domain

import model.Doctor
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import repo.DoctorsTable
import repo.toDoctor
import server.DatabaseFactory.dbtx

object Doctors {

	suspend fun with(doctor: Doctor): DoctorUnit = dbtx {
		DoctorUnit(doctor.id)
	}

	suspend fun with(doctorId: Int): DoctorUnit = dbtx {
		DoctorUnit(doctorId)
	}

	/**
	 * Finds doctor by ID.
	 */
	suspend fun findById(id: Int): Doctor? = dbtx {
		DoctorsTable.select { DoctorsTable.id eq id }.singleOrNull()?.toDoctor()
	}

	/**
	 * Returns doctor by its user id.
	 */
	suspend fun findByUserId(userId: Int): Doctor? = dbtx {
		DoctorsTable
			.select { DoctorsTable.userId eq userId }
			.singleOrNull()?.toDoctor()
	}

	suspend fun findExistingByUserId(userId: Int): Doctor = dbtx {
		DoctorsTable
			.select { DoctorsTable.userId eq userId }
			.single().toDoctor()
	}

	/**
	 * Returns all doctors, ordered by ID (added time)
	 */
	suspend fun listAllDoctors(): List<Doctor> = dbtx {
		DoctorsTable.selectAll().sortedBy { DoctorsTable.id }.toList().map { it.toDoctor() }
	}

	/**
	 * Deletes all doctors.
	 */
	suspend fun deleteAllDoctors() = dbtx {
		DoctorsTable.deleteAll()
	}
}
