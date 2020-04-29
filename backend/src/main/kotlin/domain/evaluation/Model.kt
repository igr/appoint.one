package domain.evaluation

import appoint1.annotations.GENERATED
import appoint1.annotations.IdGen
import domain.Id
import domain.patient.PatientSex
import id.EvaluationId
import id.TimeslotId
import kotlinx.serialization.Serializable

@IdGen
val _EvaluationId: Id = GENERATED()

@Serializable
data class EvaluationData(
	val sex: PatientSex,
	val age: Int,
	val problem: String,
	val help: String,
	val success: Boolean,
	val forward: Boolean,
	val comment: String
)

@Serializable
data class NewEvaluation(
	val data: EvaluationData,
	val timeslotId: TimeslotId
)

@Serializable
data class Evaluation(
	val id: EvaluationId,
	val data: EvaluationData,
	val timeslotId: TimeslotId
)
