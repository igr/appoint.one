package server.db

import domain.doctor.newSimpleDoctorUser
import domain.user.NewUser
import domain.user.UserRole
import domain.user.verbs.AddDoctor
import domain.user.verbs.AddUser
import domain.user.verbs.FindUserByUsername
import server.DatabaseFactory.dbtx

suspend fun createDevData() {
	dbtx {
		val userA = FindUserByUsername("a@a.com")
		if (userA == null) {
			AddUser(NewUser("a@a.com", "admin", UserRole.ADMIN))
		}
		val userD = FindUserByUsername("d@d.com")
		if (userD == null) {
			AddDoctor(newSimpleDoctorUser(
				name = "Proba",
				password = "doc",
				email = "d@d.com"))
		}
	}
}
