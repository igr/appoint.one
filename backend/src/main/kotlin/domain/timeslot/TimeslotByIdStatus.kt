package domain.timeslot

import domain.evaluation.EvaluationData
import domain.evaluation.EvaluationsTable
import domain.evaluation.data
import domain.evaluation.toEvaluationId
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.update
import server.DatabaseFactory.dbtx

class TimeslotByIdStatus(private val timeslotId: TimeslotId) {

	suspend fun reserveIfNew() = dbtx {
		TimeslotsTable.update({
			TimeslotsTable.id eq timeslotId.value and
				(TimeslotsTable.status eq TimeslotStatus.NEW.value)
		}) {
			it[status] = TimeslotStatus.RESERVED.value
		}
	}

	suspend fun cancelIfReserved() = dbtx {
		TimeslotsTable.update({
			TimeslotsTable.id eq timeslotId.value and
				(TimeslotsTable.status eq TimeslotStatus.RESERVED.value)
		}) {
			it[status] = TimeslotStatus.CANCELED.value
		}
	}

	suspend fun markDone(evaluationData: EvaluationData) = dbtx {
		val evolutionId = EvaluationsTable.insertAndGetId {
			it[timeslotId] = this@TimeslotByIdStatus.timeslotId.value
			evaluationData.data(it)
		}

		TimeslotsTable.update({
			TimeslotsTable.id eq timeslotId.value
		}) {
			it[status] = TimeslotStatus.DONE.value
		}

		evolutionId.toEvaluationId()
	}
}
