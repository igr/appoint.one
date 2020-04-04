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
		pic = false,
		confirmed = false,
		dateUpdated = LocalDateTime.now()
	)
}

fun newSimpleDoctor(name: String, userId: Int): NewDoctorWithUser {
	return NewDoctorWithUser(
		data = newDoctorData(name),
		userId = userId
	);
}

fun newSimpleDoctor(name: String): NewDoctorAndUser {
	return NewDoctorAndUser(
		doctor = newDoctorData(name),
		user = NewUser(
			name = name,
			password = "pass",
			role = UserRole.DOC
		)
	)
}

fun resetDate(doctor: Doctor?): Doctor? {
	val dateTime = LocalDateTime.of(2020, 5, 11, 0, 0, 0)
	val doctorCopy = doctor?.copy(data = doctor?.data.copy(dateUpdated = dateTime))
	return doctorCopy;
}
