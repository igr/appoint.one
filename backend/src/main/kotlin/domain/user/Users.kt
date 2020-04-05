package domain.user

import auth.BCryptHasher
import domain.doctor.Doctor
import domain.doctor.DoctorById
import domain.doctor.DoctorsTable
import domain.doctor.data
import org.jetbrains.exposed.sql.insertAndGetId
import server.DatabaseFactory.dbtx

object Users {

	suspend fun addUser(user: NewUser): Int = dbtx {
		val userId = UsersTable.insertAndGetId {
			it[name] = user.name
			it[password] = BCryptHasher.hashPassword(user.password)
			it[role] = user.role.value
		}
		userId.value
	}

	suspend fun addDoctor(userDoctor: NewDoctorUser): Int = dbtx {
		val userId = UsersTable.insertAndGetId {
			NewUser(
				name = userDoctor.name,
				password = userDoctor.password,
				role = UserRole.DOC
			).data(it)
		}
		DoctorsTable.insertAndGetId {
			userDoctor.doctor.data(it)
			it[id] = userId
			it[DoctorsTable.userId] = userId.value
		}

		userId.value
	}

	suspend fun addAndGetDoctor(userDoctor: NewDoctorUser): Doctor = dbtx {
		val newDoctorId = addDoctor(userDoctor)

		DoctorById(newDoctorId).existing()
	}

}
