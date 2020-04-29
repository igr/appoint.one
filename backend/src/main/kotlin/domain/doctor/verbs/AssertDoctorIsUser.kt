package domain.doctor.verbs

import domain.user.AccessDenied
import id.DoctorId
import id.UserId

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
