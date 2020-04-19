package domain.doctor.verbs

import domain.doctor.*
import org.jetbrains.exposed.sql.select

object FindExistingDoctorById : _FindExistingDoctorById {
	override fun invoke(doctorId: DoctorId): Doctor {
		return DoctorsTable
			.select { DoctorsTable.id eq doctorId.value }
			.single()
			.toDoctor()
	}
}
