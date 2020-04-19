package domain.doctor.verbs

import domain.doctor.Doctor
import domain.doctor.DoctorId
import domain.doctor.DoctorsTable
import domain.doctor.toDoctor
import org.jetbrains.exposed.sql.select

object FindExistingDoctorById : (DoctorId) -> Doctor {
	override fun invoke(doctorId: DoctorId): Doctor {
		return DoctorsTable
			.select { DoctorsTable.id eq doctorId.value }
			.single()
			.toDoctor()
	}
}
