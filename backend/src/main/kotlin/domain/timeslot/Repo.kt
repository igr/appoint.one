package domain.timeslot

import DateTime
import domain.doctor.DoctorsTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.InsertStatement

object TimeslotsTable : IntIdTable(name = "timeslots") {
	val status = integer("status").index()
	val datetime = long("datetime").index()
	val doctorId = integer("doctor_id").references(DoctorsTable.id)
}

fun NewTimeslot.data(insert: InsertStatement<EntityID<Int>>) {
	val obj = this
	with(TimeslotsTable) {
		insert[status] = TimeslotStatus.NEW.value
		insert[datetime] = obj.datetime.value
		insert[doctorId] = obj.doctorId
	}
}

fun ResultRow.toTimeslot() = Timeslot(
	id = this[TimeslotsTable.id].value,
	datetime = DateTime(this[TimeslotsTable.datetime]),
	status = TimeslotStatus.of(this[TimeslotsTable.status]),
	doctorId = this[TimeslotsTable.doctorId]
)
