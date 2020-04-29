package auth

import domain.user.User
import domain.user.verbs.FindUserByUsername
import io.ktor.application.ApplicationCall
import io.ktor.auth.Credential
import io.ktor.auth.authentication
import kotlinx.serialization.Serializable
import server.DatabaseFactory.dbtx

@Serializable
data class LoginCredential(val name: String, val password: String) : Credential

val ApplicationCall.user: User? get() = authentication.principal()

object Auth {

	suspend fun login(credentials: LoginCredential): User = credentials.let { (name, password) ->
		val user = dbtx {
			FindUserByUsername(name) ?: throw UserNotFound
		}
		BCryptHasher.checkPassword(password, user.password)
		val token = JwtConfig.makeToken(user)
		return user.copy(token = token)
	}
}

//	suspend fun register(newUser: NewUser): User {
//		val userId = Users.addNewUser(newUser)
//		val user = UsersTable.select { UsersTable.id eq userId }.single().toUser()
//		return user.copy(token = JwtConfig.makeToken(user))
//	}

//	fun updateUser(new: User, current: User): User {
//		val final = new.copy(password = BCryptHasher.hashPassword(new.password))
//		return db.updateUser(final, current) ?: throw UserNotFound
//	}

