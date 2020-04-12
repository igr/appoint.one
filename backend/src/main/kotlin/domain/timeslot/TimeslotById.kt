package domain.timeslot

import domain.evaluation.EvaluationData
import domain.evaluation.EvaluationsTable
import domain.evaluation.data
import domain.evaluation.toEvaluationId
import domain.user.AccessDenied
import domain.user.UserId
import org.jetbrains.exposed.sql.*
import server.DatabaseFactory.dbtx

class TimeslotById(private val timeslotId: TimeslotId) {

	suspend fun get(): Timeslot? = dbtx {
		TimeslotsTable.select { TimeslotsTable.id eq timeslotId.value }.singleOrNull()?.toTimeslot()
	}

	suspend fun delete() = dbtx {
		TimeslotsTable.deleteWhere { TimeslotsTable.id eq timeslotId.value }
	}

	suspend fun reserveIfNew() = dbtx {
		TimeslotsTable.update({
			TimeslotsTable.id eq timeslotId.value and
				(TimeslotsTable.status eq TimeslotStatus.NEW.value)
		}) {
			it[status] = TimeslotStatus.RESERVED.value
		}
	}

	suspend fun activateIfReserved() = dbtx {
		TimeslotsTable.update({
			TimeslotsTable.id eq timeslotId.value and
				(TimeslotsTable.status eq TimeslotStatus.RESERVED.value)
		}) {
			it[status] = TimeslotStatus.NEW.value
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
			it[timeslotIdRef] = timeslotId.value
			evaluationData.data(it)
		}

		TimeslotsTable.update({
			TimeslotsTable.id eq timeslotId.value
		}) {
			it[status] = TimeslotStatus.DONE.value
		}

		evolutionId.toEvaluationId()
	}

	suspend fun assertOwnership(userId: UserId?): TimeslotById = dbtx {
		if (userId == null) {
			throw AccessDenied()
		}
		// todo convert userid to doctorid, even its dummy
		val count = TimeslotsTable
			.slice(TimeslotsTable.id)
			.select { TimeslotsTable.id eq timeslotId.value }
			.andWhere { TimeslotsTable.doctorIdRef eq userId.value }
			.count()

		if (count != 1L) {
			throw AccessDenied()
		}
		this
	}

}
