package model

/**
 * Creates new doctor.
 */
fun newSimpleDoctor(name: String): NewDoctor {
	return NewDoctor(
		name = name,
		sex = DoctorSex.MALE,
		country = Country.SERBIA,
		year = 1990,
		occupation = "Occupation",
		education = 4,
		phone = "641294217",
		zoom = "Z-00-M",
		pic = false
	);
}
