package domain.doctor

import domain.user.NewDoctorUser
import server.Env

fun NewDoctorUser.assertValidDoctorRegCode(): NewDoctorUser {
	if (this.regCode != Env.REG_CODE) {
		throw InvalidDoctorRegistrationCode()
	}
	return this
}
