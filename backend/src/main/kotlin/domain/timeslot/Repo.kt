package domain.timeslot

import DateTime
import domain.doctor.DoctorsTable
import domain.doctor.toDoctorId
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.`java-time`.datetime
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import java.time.LocalDateTime

object TimeslotsTable : IntIdTable(name = "timeslots") {
	val status = integer("status").index()
	val datetime = long("datetime").index()

	// ref
	val doctorIdRef = integer("doctor_id").references(DoctorsTable.id)

	// meta
	val updated = datetime("updated").clientDefault { LocalDateTime.now() }
}

fun NewTimeslot.data(insert: UpdateBuilder<*>) {
	val obj = this
	with(TimeslotsTable) {
		insert[status] = TimeslotStatus.NEW.value
		insert[datetime] = obj.datetime.value
		insert[doctorIdRef] = obj.doctorId.value
		insert[updated] = LocalDateTime.now()
	}
}

fun ResultRow.toTimeslot() = Timeslot(
	id = this[TimeslotsTable.id].value.toTimeslotId(),
	datetime = DateTime(this[TimeslotsTable.datetime]),
	status = TimeslotStatus.of(this[TimeslotsTable.status]),
	doctorId = this[TimeslotsTable.doctorIdRef].toDoctorId()
)
