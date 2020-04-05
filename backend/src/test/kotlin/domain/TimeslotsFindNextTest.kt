package domain

import server.ServerTest

class TimeslotsFindNextTest : ServerTest() {

//	@Test
//	fun `find next available timeslot`() = runBlocking {
//		// given
//		val user1 = Users.registerUser(newSimpleUserWithDoctorRole("pera"))
//		val doctor1 = Doctors.addNewDoctor(newSimpleDoctor("doc1", user1.id))
//		val futureTimeslot: DateTime = LocalDateTime.now().plusHours(1).toDateTime()
//		val expiredTimeslot: DateTime = LocalDateTime.now().minusDays(1).toDateTime()
//		Doctors.with(doctor1).bindTimeslots(listOf(futureTimeslot, expiredTimeslot))
//
//		// when
//		val timeslots = Timeslots.findNextTimeslots(Country_SERBIA)
//
//		// then
//		assertThat(timeslots.size).isEqualTo(1)
//		assertThat(timeslots[0].datetime).isEqualTo(futureTimeslot)
//		assertThat(timeslots[0].doctor.id).isEqualTo(doctor1.id)
//
//		Unit
//	}
//
}
