package domain.user.verbs

import domain.doctor.DoctorsTable
import domain.doctor.assertValidDoctorRegCode
import domain.doctor.data
import domain.user.*
import id.DoctorId
import id.toDoctorId
import org.jetbrains.exposed.sql.insertAndGetId

object AddDoctor : (NewDoctorUser) -> DoctorId {
	override fun invoke(newDoctorUser: NewDoctorUser): DoctorId {
		newDoctorUser.assertValidDoctorRegCode()

		val uid = UsersTable.insertAndGetId {
			NewUser(
				name = newDoctorUser.name,
				password = newDoctorUser.password,
				role = UserRole.DOC
			).data(it)
		}

		return DoctorsTable.insertAndGetId {
			newDoctorUser.doctor.data(it)
			it[id] = uid
			it[userIdRef] = uid.value
		}
			.toDoctorId()
	}

}
