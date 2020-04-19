package domain.doctor.verbs

import domain.doctor.*
import domain.user.UsersTable
import org.jetbrains.exposed.sql.update

object UpdateDoctorData : _UpdateDoctorData {
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
