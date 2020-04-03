package model

import java.time.LocalDateTime

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

/**
 * Doctor + User.
 */
data class Doctor(
	val id: Int,
	val data: DoctorData,
	val user: User
)

data class NewDoctorWithUser(
	val data: DoctorData,
	val userId: Int
)

data class NewDoctorAndUser(
	val doctor: DoctorData,
	val user: NewUser
)
