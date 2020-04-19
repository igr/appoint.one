package domain.doctor.verbs

import domain.doctor.DoctorId
import domain.user.AccessDenied
import domain.user.UserId

object AssertDoctorIsUser : (DoctorId, UserId?) -> Unit {
	override fun invoke(doctorId: DoctorId, userId: UserId?): Unit {
		if (userId == null) {
			throw AccessDenied()
		}
		if (doctorId.value != userId.value) {
			throw AccessDenied()
		}
	}
}
