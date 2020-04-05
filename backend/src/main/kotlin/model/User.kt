package model

import com.fasterxml.jackson.annotation.JsonIgnore
import io.ktor.auth.Principal

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
	val name: String,
	@JsonIgnore
	val password: String = "",
	val role: UserRole,
	val token: String = ""
) : Principal

data class NewUser(
	val name: String,
	val password: String,
	val role: UserRole = UserRole.GUEST
)

data class NewDoctorUser(
	val name: String,
	val password: String,
	val doctor: DoctorData
)
