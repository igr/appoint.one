package server.db

import domain.Doctors
import domain.Users
import kotlinx.coroutines.runBlocking
import model.Country
import model.NewDoctor
import model.NewUser
import model.UserRole

fun createDevAdmin() {
	runBlocking {
		Users.registerUser(NewUser("admin", "admin", UserRole.ADMIN))
		Users.registerUser(NewUser("doc", "doc", UserRole.DOC))
		Doctors.addNewDoctor(NewDoctor("Pera", Country.SERBIA))
	}
}
