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
		Users.addNewUser(NewUser("a@a.com", "admin", UserRole.ADMIN))
		val doc = Users.addNewDoctorUser(newSimpleDoctor("Pera"))

		val nextTimeslot = LocalDateTime.now().plusDays(1).toDateTime()
		Doctors.with(doc).bindTimeslots(listOf(nextTimeslot))
	}
}
