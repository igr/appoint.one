package domain.patient

import kotlinx.serialization.Serializable

@Serializable(with = PatientSexSerializer::class)
enum class PatientSex(val value: Int) {
	FEMALE(0), MALE(1), OTHER(2);

	companion object {
		fun of(value: Int): PatientSex = when (value) {
			0 -> FEMALE
			1 -> MALE
			2 -> OTHER
			else -> throw Error("Invalid patient sex")
		}
	}
}
