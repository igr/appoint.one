package domain.evaluation

import domain.timeslot.TimeslotsTable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.datetime
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import java.time.LocalDateTime

object EvaluationsTable : IntIdTable(name = "evaluations") {
	val sex = integer("sex")
	val age = integer("age")
	val problem = varchar("problem", 1024)
	val help = varchar("help", 1024)
	val success = bool("success")
	val forward = bool("forward")
	val comment = varchar("comment", 1024)

	// ref
	val timeslotIdRef = integer("timeslot_id").references(TimeslotsTable.id)

	// meta
	val updated = datetime("updated").clientDefault { LocalDateTime.now() }
}

fun EvaluationData.data(insert: UpdateBuilder<*>) {
	val obj = this
	with(EvaluationsTable) {
		insert[sex] = obj.sex.value
		insert[age] = obj.age
		insert[problem] = obj.problem
		insert[help] = obj.help
		insert[success] = obj.success
		insert[forward] = obj.forward
		insert[comment] = obj.comment
		insert[updated] = LocalDateTime.now()
	}
}
