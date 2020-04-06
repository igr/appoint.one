package domain.user

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonValue
import domain.Id
import domain.doctor.DoctorData
import io.ktor.auth.Principal
import org.jetbrains.exposed.dao.id.EntityID

data class UserId @JsonCreator(mode = JsonCreator.Mode.DELEGATING) constructor(
	@JsonValue override val value: Int
) : Id()

fun Int.toUserId(): UserId {
	return UserId(this)
}

fun String.toUserId(): UserId {
	return UserId(this.toInt())
}

fun EntityID<Int>.toUserId(): UserId {
	return UserId(this.value);
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
	val id: UserId,
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
