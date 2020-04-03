package server.db

import domain.Doctors
import domain.Users
import kotlinx.coroutines.runBlocking
import model.NewUser
import model.UserRole
import model.newSimpleDoctor
import toDateTime
import java.time.LocalDateTime

fun createDevData() {
	runBlocking {
		val admin = Users.registerUser(NewUser("a@a.com", "admin", UserRole.ADMIN))
		val doc = Users.registerUser(NewUser("d@d.com", "doc", UserRole.DOC))

		val doctor = Doctors.addNewDoctor(newSimpleDoctor("Pera", doc.id))

		val nextTimeslot = LocalDateTime.now().plusDays(1).toDateTime()
		Doctors.with(doctor).bindTimeslots(listOf(nextTimeslot))
	}
}
