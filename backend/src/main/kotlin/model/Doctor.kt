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
	val confirmed: Boolean,
	val dateUpdated: LocalDateTime = LocalDateTime.now()
)

/**
 * Doctor.
 */
data class Doctor(
	val id: Int,
	val data: DoctorData,
	val userId: Int
) {

	fun resetDate(): Doctor {
		val dateTime = LocalDateTime.of(2020, 5, 11, 0, 0, 0)
		return copy(data = data.copy(dateUpdated = dateTime));
	}

}

