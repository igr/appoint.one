package domain.doctor

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import domain.Id
import org.jetbrains.exposed.dao.id.EntityID
import java.time.LocalDateTime

data class DoctorId @JsonCreator(mode = JsonCreator.Mode.DELEGATING) constructor(
	@JsonValue override val value: Int
) : Id()

fun Int.toDoctorId(): DoctorId {
	return DoctorId(this)
}

fun String.toDoctorId(): DoctorId {
	return DoctorId(this.toInt())
}

fun EntityID<Int>.toDoctorId(): DoctorId {
	return DoctorId(this.value);
}

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
) {

	fun lockUpdateDate(): DoctorData {
		val dateTime = LocalDateTime.of(2020, 5, 11, 1, 7, 3)
		return copy(dateUpdated = dateTime)
	}
}

data class Doctor(
	val id: DoctorId,
	val data: DoctorData,
	val userId: Int
)
