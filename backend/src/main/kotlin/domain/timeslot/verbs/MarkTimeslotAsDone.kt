package domain.timeslot.verbs

import domain.evaluation.*
import domain.timeslot.TimeslotId
import domain.timeslot.TimeslotStatus
import domain.timeslot.TimeslotsTable
import domain.timeslot._MarkTimeslotAsDone
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.update

object MarkTimeslotAsDone : _MarkTimeslotAsDone {
	override fun invoke(timeslotId: TimeslotId, evaluationData: EvaluationData): EvaluationId {
		val evolutionId = EvaluationsTable.insertAndGetId {
			it[timeslotIdRef] = timeslotId.value
			evaluationData.data(it)
		}

		TimeslotsTable.update({
			TimeslotsTable.id eq timeslotId.value
		}) {
			it[status] = TimeslotStatus.DONE.value
		}

		return evolutionId.toEvaluationId()
	}
}
