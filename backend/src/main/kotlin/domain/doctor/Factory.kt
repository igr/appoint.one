package domain.doctor

import domain.evaluation.EvaluationData
import domain.patient.PatientSex
import domain.user.NewDoctorUser
import server.Env

private fun newDoctorData(name: String, email: String = "${name}@dot.com"): DoctorData {
	return DoctorData(
		name = name,
		email = email,
		sex = DoctorSex.MALE,
		year = 1990,
		education = 4,
		occupation = 1,
		occupation2 = "",
		occupationSpec = "",
		certificate = DoctorCertificate.NONE,
		modalitet = 2,
		modalitet2 = "",
		phone = "641294217",
		zoom = "Z-00-M"
	)
}

fun newSimpleDoctorUser(name: String, password: String = "pass", email: String = name): NewDoctorUser {
	return NewDoctorUser(
		doctor = newDoctorData(name, email),
		name = email,
		password = password,
		regCode = Env.REG_CODE
	)
}

fun newSimpleEvaluationData(): EvaluationData {
	return EvaluationData(
		sex = PatientSex.OTHER,
		age = 22,
		comment = "Comment",
		forward = true,
		help = "Help",
		problem = "Problem",
		success = false
	)
}
