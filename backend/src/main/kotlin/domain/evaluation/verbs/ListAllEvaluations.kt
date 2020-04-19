package domain.evaluation.verbs

import domain.evaluation.Evaluation
import domain.evaluation.EvaluationsTable
import domain.evaluation._ListAllEvaluations
import domain.evaluation.toEvaluation
import org.jetbrains.exposed.sql.selectAll

object ListAllEvaluations : _ListAllEvaluations {
	override fun invoke(): List<Evaluation> {
		return EvaluationsTable
			.selectAll()
			.sortedBy { EvaluationsTable.id }
			.toList()
			.map { it.toEvaluation() }
	}
}
