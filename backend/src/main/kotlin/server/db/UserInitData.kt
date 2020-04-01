package server.db

import domain.Doctors
import domain.Users
import kotlinx.coroutines.runBlocking
import model.NewUser
import model.UserRole
import model.newSimpleDoctor

fun createDevAdmin() {
	runBlocking {
		val admin = Users.registerUser(NewUser("a@a.com", "admin", UserRole.ADMIN))
		val doc = Users.registerUser(NewUser("d@d.com", "doc", UserRole.DOC))
		Doctors.addNewDoctor(newSimpleDoctor("Pera", doc.id))
	}
}
