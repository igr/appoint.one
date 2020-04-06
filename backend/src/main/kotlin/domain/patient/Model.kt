package domain.patient

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class PatientSex @JsonCreator(mode = JsonCreator.Mode.DELEGATING) constructor(@JsonValue val value: Int) {
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
