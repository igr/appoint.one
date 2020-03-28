package auth

import domain.Users
import io.ktor.application.ApplicationCall
import io.ktor.auth.authentication
import model.EmailPasswordCredential
import model.NewUser
import model.User

val ApplicationCall.user: User? get() = authentication.principal()

object Auth {

	suspend fun login(credentials: EmailPasswordCredential): User = credentials.let { (email, password) ->
		val user = Users.findUserByEmail(email) ?: throw UserNotFound
		BCryptHasher.checkPassword(password, user.password)
		val token = JwtConfig.makeToken(user)
		return user.copy(token = token)
	}

	suspend fun register(newUser: NewUser): User {
		return Users.registerUser(newUser).run {
			copy(token = JwtConfig.makeToken(this))
		}
	}

//	fun updateUser(new: User, current: User): User {
//		val final = new.copy(password = BCryptHasher.hashPassword(new.password))
//		return db.updateUser(final, current) ?: throw UserNotFound
//	}

}