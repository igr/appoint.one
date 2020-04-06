package domain.evaluation

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import domain.Id
import domain.patient.PatientSex

data class EvaluationId @JsonCreator(mode = JsonCreator.Mode.DELEGATING) constructor(
	@JsonValue override val value: Int
) : Id()


data class EvaluationData(
	val sex: PatientSex,
	val age: Int,
	val problem: String,
	val help: String,
	val success: Boolean,
	val forward: Boolean,
	val comment: String
)

data class NewEvaluation(
	val data: EvaluationData,
	val timeslotId: Int
)

data class Evaluation(
	val id: EvaluationId,
	val data: EvaluationData,
	val timeslotId: Int
)
