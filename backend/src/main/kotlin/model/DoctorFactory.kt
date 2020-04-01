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

/**
 * Creates new doctor.
 */
fun newSimpleDoctor(name: String, userId: UserId): NewDoctorWithUser {
	return NewDoctorWithUser(
		data = DoctorData(
			name = name,
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
		),
		userId = userId
	);
}

fun newSimpleDoctor(name: String): NewDoctorAndUser {
	return NewDoctorAndUser(
		doctor = DoctorData(
			name = name,
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
		),
		user = NewUser(
			name = name,
			password = "pass",
			role = UserRole.DOC
		)
	);
}
