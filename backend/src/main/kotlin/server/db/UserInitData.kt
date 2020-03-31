package server.db

import domain.Doctors
import domain.Users
import kotlinx.coroutines.runBlocking
import model.NewUser
import model.UserRole
import model.newSimpleDoctor

fun createDevAdmin() {
	runBlocking {
		Users.registerUser(NewUser("a@a.com", "admin", UserRole.ADMIN))
		Users.registerUser(NewUser("d@d.com", "doc", UserRole.DOC))
		Doctors.addNewDoctor(newSimpleDoctor("Pera"))
	}
}
