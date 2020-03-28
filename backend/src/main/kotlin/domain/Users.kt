package domain

import auth.BCryptHasher
import model.NewUser
import model.User
import model.UserEntity
import model.UsersRepo
import org.jetbrains.exposed.sql.deleteAll
import server.DatabaseFactory.dbtx

@TargetIs("all users")
object Users {

	suspend fun registerUser(user: NewUser): User = dbtx {
		// check if user already exist
//		if (UserEntity.find { UsersRepo.email eq user.email }.any()) {
//			throw UserAlreadyExists
//		}

		// save user
		UserEntity.new {
			email = user.email
			password = BCryptHasher.hashPassword(user.password)
		}.toUser()
	}
	
	suspend fun findUserByEmail(email: String): User? = dbtx {
		UserEntity.find { UsersRepo.email eq email }.firstOrNull()?.toUser()
	}

	/**
	 * Deletes all users.
	 */
	suspend fun deleteAllUsers() = dbtx {
		UsersRepo.deleteAll()
	}

}
