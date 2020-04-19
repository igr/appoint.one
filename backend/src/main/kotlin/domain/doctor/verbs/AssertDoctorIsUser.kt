package domain.doctor.verbs

import domain.doctor.DoctorId
import domain.doctor._AssertDoctorIsUser
import domain.user.AccessDenied
import domain.user.UserId

object AssertDoctorIsUser : _AssertDoctorIsUser {
	override fun invoke(doctorId: DoctorId, userId: UserId?): Unit {
		if (userId == null) {
			throw AccessDenied()
		}
		if (doctorId.value != userId.value) {
			throw AccessDenied()
		}
	}
}
