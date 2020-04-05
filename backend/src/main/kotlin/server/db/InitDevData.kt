package server.db

import domain.doctor.DoctorTimeslots
import domain.doctor.newSimpleDoctorUser
import domain.user.NewUser
import domain.user.UserRole
import domain.user.Users
import kotlinx.coroutines.runBlocking
import toDateTime
import java.time.LocalDateTime

fun createDevData() {
	runBlocking {
		Users.addUser(NewUser("a@a.com", "admin", UserRole.ADMIN))
		val doctorId = Users.addDoctor(newSimpleDoctorUser(
			name = "Petar Petrović",
			password = "doc",
			email = "d@d.com"))

		val nextTimeslot = LocalDateTime.now().plusDays(1).toDateTime()
		DoctorTimeslots(doctorId).bindTimeslots(listOf(nextTimeslot))
	}
}
