package domain.doctor

import appoint1.annotations.GENERATED
import appoint1.annotations.IdGen
import domain.Id
import id.DoctorId
import kotlinx.serialization.Serializable

@IdGen
val _DoctorId: Id = GENERATED()

@Serializable
enum class DoctorSex(val value: Boolean) {
	MALE(true), FEMALE(false);

	companion object {
		fun of(value: Boolean): DoctorSex = if (value) MALE else FEMALE;
	}
}

@Serializable(with = DoctorCertificateSerializer::class)
enum class DoctorCertificate(val value: Int) {
	NONE(0), NATIONAL(1), INTERNATIONAL(2);

	companion object {
		fun of(value: Int): DoctorCertificate = when (value) {
			0 -> NONE
			1 -> NATIONAL
			2 -> INTERNATIONAL
			else -> throw Error("Invalid certificate")
		}
	}
}

@Serializable
data class DoctorData(
	val name: String,
	val email: String,
	val sex: DoctorSex,
	val year: Int,
	val education: Int,
	val occupation: Int,
	val occupation2: String = "",
	val occupationSpec: String = "",
	val certificate: DoctorCertificate,
	val modalitet: Int,
	val modalitet2: String = "",
	val phone: String,
	val zoom: String
)

@Serializable
data class Doctor(
	val id: DoctorId,
	val data: DoctorData,
	val userId: Int
)


data class DoctorWithStats(
	val doctor: Doctor,
	val newStatusCount: Int,
	val canceledStatusCount: Int,
	val doneStatusCount: Int,
	val reservedStatusCount: Int
)
