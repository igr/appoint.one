package server.db

import domain.doctor.newSimpleDoctorUser
import domain.user.NewUser
import domain.user.UserRole
import domain.user.verbs.AddDoctor
import domain.user.verbs.AddUser
import server.DatabaseFactory.dbtx

suspend fun createDevData() {
	dbtx {
		AddUser(NewUser("a@a.com", "admin", UserRole.ADMIN))
		AddDoctor(newSimpleDoctorUser(
			name = "Proba",
			password = "doc",
			email = "d@d.com"))
	}
}
