package domain.doctor.verbs

import domain.doctor.DoctorWithStats
import domain.doctor.DoctorsTable
import domain.doctor.toDoctor
import domain.timeslot.TimeslotStatus
import domain.timeslot.TimeslotsTable
import org.jetbrains.exposed.sql.*

object ListAllDoctorsWithStats : () -> List<DoctorWithStats> {

	private val statusExpr = TimeslotsTable.status

	private fun sumByStatus(status: Int) = Expression.build {
		val caseExpr = case().When(statusExpr eq intLiteral(status), intLiteral(1)).Else(intLiteral(0))
		Sum(caseExpr, IntegerColumnType())
	}

	// select sum(case when status = 10 then 1 else 0 end) as s10 from timeslots group by doctor_id

	override fun invoke(): List<DoctorWithStats> {
		val new = sumByStatus(TimeslotStatus.NEW.value)
		val canceled = sumByStatus(TimeslotStatus.CANCELED.value)
		val done = sumByStatus(TimeslotStatus.DONE.value)
		val reserved = sumByStatus(TimeslotStatus.RESERVED.value)

		val columns = mutableListOf<Expression<*>>(new, canceled, done, reserved)
		columns.addAll(DoctorsTable.columns)

		return (TimeslotsTable innerJoin DoctorsTable)
			.slice(columns)
			.selectAll()
			.groupBy(TimeslotsTable.doctorIdRef, DoctorsTable.id)
			.map {
				DoctorWithStats(
					newStatusCount = it[new] ?: 0,
					canceledStatusCount = it[canceled] ?: 0,
					doneStatusCount = it[done] ?: 0,
					reservedStatusCount = it[reserved] ?: 0,
					doctor = it.toDoctor()
				)
			}
			.toList()
	}
}
