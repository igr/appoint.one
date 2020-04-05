package model

import java.time.LocalDateTime

/**
 * Creates new users.
 */
fun newSimpleUserWithDoctorRole(name: String): NewUser {
	return NewUser(
		name = name,
		role = UserRole.DOC,
		password = "doc"
	)
}

private fun newDoctorData(name: String): DoctorData {
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

fun newSimpleDoctor(name: String): NewDoctorUser {
	return NewDoctorUser(
		doctor = newDoctorData(name),
		name = name,
		password = "pass"
	)
}
