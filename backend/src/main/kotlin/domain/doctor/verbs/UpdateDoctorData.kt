package domain.doctor.verbs

import domain.doctor.DoctorData
import domain.doctor.DoctorId
import domain.doctor.DoctorsTable
import domain.doctor.data
import domain.user.UsersTable
import org.jetbrains.exposed.sql.update

object UpdateDoctorData : (DoctorId, DoctorData) -> Unit {
	override fun invoke(doctorId: DoctorId, doctorData: DoctorData) {
		DoctorsTable.update({
			DoctorsTable.id eq doctorId.value
		}) {
			doctorData.data(it)
		}

		UsersTable.update({
			UsersTable.id eq doctorId.value
		}) {
			it[name] = doctorData.email
		}
	}
}
