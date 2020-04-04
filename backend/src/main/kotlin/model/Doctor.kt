package model

import java.time.LocalDateTime

enum class DoctorSex(val value: Boolean) {
	MALE(true), FEMALE(false);

	companion object {
		fun of(value: Boolean): DoctorSex = if (value) MALE else FEMALE;
	}
}

enum class DoctorCertificate(val value: Int) {
	NONE(1), NATIONAL(2), INTERNATIONAL(3);

	companion object {
		fun of(value: Int): DoctorCertificate = when (value) {
			1 -> NONE
			2 -> NATIONAL
			3 -> INTERNATIONAL
			else -> throw Error("Invalid certificate")
		}
	}
}


data class DoctorData(
	val name: String,
	val email: String,
	val sex: DoctorSex,
//	val country: Country,
//	val city: City,
	val year: Int,
	val education: Int,
	val occupation: Int,
	val occupation2: String,
	val occupationSpec: String,
	val certificate: DoctorCertificate,
	val modalitet: Int,
	val modalitet2: String,
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
