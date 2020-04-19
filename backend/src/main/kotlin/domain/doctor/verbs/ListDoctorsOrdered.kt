package domain.doctor.verbs

import domain.doctor.Doctor
import domain.doctor.DoctorsTable
import domain.doctor._ListDoctorsOrdered
import domain.doctor.toDoctor
import org.jetbrains.exposed.sql.selectAll

object ListDoctorsOrdered : _ListDoctorsOrdered {
	override fun invoke(): List<Doctor> {
		return DoctorsTable
			.selectAll()
			.sortedBy { DoctorsTable.id }
			.toList()
			.map { it.toDoctor() }
	}
}
