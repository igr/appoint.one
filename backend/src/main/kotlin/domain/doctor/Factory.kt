package domain.doctor

import domain.user.NewDoctorUser
import java.time.LocalDateTime

private fun newDoctorData(name: String, email: String = "${name}@dot.com"): DoctorData {
	return DoctorData(
		name = name,
		email = "${name}@dot.com",
		sex = DoctorSex.MALE,
//		country = Country_SERBIA,
		year = 1990,
		education = 4,
		occupation = 1,
		occupation2 = "",
		occupationSpec = "",
		certificate = DoctorCertificate.NONE,
		modalitet = 2,
		modalitet2 = "",
		phone = "641294217",
		zoom = "Z-00-M",
		confirmed = false,
		dateUpdated = LocalDateTime.now()
	)
}

fun newSimpleDoctorUser(name: String, password: String = "pass", email: String = name): NewDoctorUser {
	return NewDoctorUser(
		doctor = newDoctorData(name, email),
		name = email,
		password = password
	)
}
