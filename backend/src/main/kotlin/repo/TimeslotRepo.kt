package repo

import DateTime
import model.NewTimeslot
import model.Timeslot
import model.TimeslotStatus
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.InsertStatement

object TimeslotsTable : IntIdTable(name = "timeslots") {
	val status = integer("status").index()
	val datetime = long("datetime").index()
	val doctorId = integer("doctor_id").references(DoctorsTable.id)
}

fun NewTimeslot.data(): TimeslotsTable.(InsertStatement<Number>) -> Unit {
	return {
		it[status] = TimeslotStatus.NEW.value
		it[datetime] = this@data.datetime.value
		it[doctorId] = this@data.doctorId
	}
}

fun ResultRow.toTimeslot() = Timeslot(
	id = this[TimeslotsTable.id].value,
	datetime = DateTime(this[TimeslotsTable.datetime]),
	status = TimeslotStatus.of(this[TimeslotsTable.status]),
	doctorId = this[TimeslotsTable.doctorId]
)
