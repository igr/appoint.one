package domain

import model.NewDoctorUser
import model.NewUser
import model.User
import model.UserRole
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import repo.DoctorsTable
import repo.UsersTable
import repo.data
import repo.toUser
import server.DatabaseFactory.dbtx

object Users {

	suspend fun addNewUser(user: NewUser): Int = dbtx {
		val userId = UsersTable.insert { user.data() } get (UsersTable.id)
		userId.value
	}

	suspend fun addNewDoctorUser(userDoctor: NewDoctorUser): Int = dbtx {
		val userId = UsersTable.insert {
			NewUser(
				name = userDoctor.name,
				password = userDoctor.password,
				role = UserRole.DOC
			).data()
		} get (UsersTable.id)

		DoctorsTable.insert {
			userDoctor.doctor.data()
			it[id] = userId
			it[DoctorsTable.userId] = userId.value
		}

		userId.value
	}

	suspend fun findUserByUsername(name: String): User? = dbtx {
		UsersTable.select { UsersTable.name eq name }.singleOrNull()?.toUser()
	}

	/**
	 * Deletes all users.
	 */
	suspend fun deleteAllUsers() = dbtx {
		UsersTable.deleteAll()
	}

}
