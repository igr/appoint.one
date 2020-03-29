package model

import io.ktor.auth.Credential
import io.ktor.auth.Principal
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

data class EmailPasswordCredential(val email: String, val password: String) : Credential

object UsersRepo : IntIdTable(name = "users") {
	val email = varchar("email", 128).uniqueIndex()
	val password = varchar("password", length = 255)
	val role = integer("role")
}

class UserEntity(id: EntityID<Int>) : Entity<Int>(id) {
	companion object : EntityClass<Int, UserEntity>(UsersRepo)

	var email by UsersRepo.email
	var password by UsersRepo.password
	var role by UsersRepo.role

	fun toUser(): User {
		return User(
			id = id.value,
			password = password,
			email = email,
			role = UserRole.of(role)
		)
	}
}

enum class UserRole(val value: Int) {
	GUEST(0),
	DOC(10),
	ADMIN(99);

	companion object {
		fun of(id: Int): UserRole = values().find { it.value == id }!!
	}
}

data class User(
	val id: Int,
	val email: String,
	val password: String,
	val role: UserRole,
	val token: String = ""
) : Principal

data class NewUser(
	val email: String,
	val password: String,
	val role: UserRole = UserRole.GUEST
)