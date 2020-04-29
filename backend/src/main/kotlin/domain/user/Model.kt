package domain.user

import appoint1.annotations.GENERATED
import appoint1.annotations.IdGen
import domain.Id
import domain.doctor.DoctorData
import id.UserId
import io.ktor.auth.Principal
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@IdGen
val _UserId: Id = GENERATED()

@Serializable
enum class UserRole(val value: Int) {
	GUEST(0),
	DOC(10),
	ADMIN(99);

	companion object {
		fun of(id: Int): UserRole = values().find { it.value == id }!!
	}
}

@Serializable
data class User(
	val id: UserId,
	val name: String,
	@Transient
	val password: String = "",
	val role: UserRole,
	val token: String = ""
) : Principal

@Serializable
data class NewUser(
	val name: String,
	val password: String,
	val role: UserRole = UserRole.GUEST
)

@Serializable
data class Password(
	val password: String
)

@Serializable
data class NewDoctorUser(
	val name: String,
	val password: String,
	val regCode: String,
	val doctor: DoctorData
)
