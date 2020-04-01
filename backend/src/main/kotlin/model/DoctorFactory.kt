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
		country = Country.SERBIA,
		year = 1990,
		occupation = "Occupation",
		education = 4,
		phone = "641294217",
		zoom = "Z-00-M",
		pic = false,
		confirmed = false,
		dateUpdated = LocalDateTime.now()
	)
}

fun newSimpleDoctor(name: String, userId: UserId): NewDoctorWithUser {
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
	);
}
