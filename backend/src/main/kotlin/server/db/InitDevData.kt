package server.db

import domain.doctor.newSimpleDoctorUser
import domain.user.NewUser
import domain.user.UserRole
import domain.user.Users
import kotlinx.coroutines.runBlocking

fun createDevData() {
	runBlocking {
		Users.addUser(NewUser("a@a.com", "admin", UserRole.ADMIN))
		Users.addDoctor(newSimpleDoctorUser(
			name = "Proba",
			password = "doc",
			email = "d@d.com"))
	}
}
