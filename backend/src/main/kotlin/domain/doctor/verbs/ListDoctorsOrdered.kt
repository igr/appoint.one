package domain.doctor.verbs

import domain.doctor.Doctor
import domain.doctor.DoctorsTable
import domain.doctor.toDoctor
import org.jetbrains.exposed.sql.selectAll

object ListDoctorsOrdered : () -> List<Doctor> {
	override fun invoke(): List<Doctor> {
		return DoctorsTable
			.selectAll()
			.sortedBy { DoctorsTable.id }
			.toList()
			.map { it.toDoctor() }
	}
}
