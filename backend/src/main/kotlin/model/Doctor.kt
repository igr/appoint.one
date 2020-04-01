package model

import java.time.LocalDateTime


data class DoctorId(
	val value: Int
)

enum class DoctorSex(val value: Boolean) {
	MALE(true), FEMALE(false);

	companion object {
		fun of(value: Boolean): DoctorSex = if (value) MALE else FEMALE;
	}
}

data class DoctorData(
	val name: String,
	val email: String,
	val sex: DoctorSex,
	val country: Country,
//	val city: City,
	val year: Int,
	val occupation: String,
	val education: Int,
	val phone: String,
	val zoom: String,
	val pic: Boolean,
	val confirmed: Boolean,
	val dateUpdated: LocalDateTime = LocalDateTime.now()
)

abstract class Foo {
	abstract val a: String;
}

data class Doctor(
	val id: DoctorId,
	val data: DoctorData,
	val user: User
)

data class NewDoctorWithUser(
	val data: DoctorData,
	val userId: UserId
)

data class NewDoctorAndUser(
	val doctor: DoctorData,
	val user: NewUser
)
