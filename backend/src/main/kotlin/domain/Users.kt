package domain

import model.*
import org.jetbrains.exposed.sql.deleteAll
import server.DatabaseFactory.dbtx

@TargetIs("all users")
object Users {

	suspend fun registerUser(user: NewUser): User = dbtx {
		// check if user already exist
//		if (UserEntity.find { UsersRepo.email eq user.email }.any()) {
//			throw UserAlreadyExists
//		}
		UserEntity.add(user).toUser()
	}

	suspend fun findUserByUsername(name: String): User? = dbtx {
		UserEntity.find { UsersRepo.name eq name }.firstOrNull()?.toUser()
	}

	/**
	 * Deletes all users.
	 */
	suspend fun deleteAllUsers() = dbtx {
		UsersRepo.deleteAll()
	}

}
