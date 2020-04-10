package domain.evaluation

import org.jetbrains.exposed.sql.selectAll
import server.DatabaseFactory.dbtx

object EvaluationsLists {
	suspend fun allEvaluationsOrdered(): List<Evaluation> = dbtx {
		EvaluationsTable.selectAll().sortedBy { EvaluationsTable.id }.toList().map { it.toEvaluation() }
	}

}
