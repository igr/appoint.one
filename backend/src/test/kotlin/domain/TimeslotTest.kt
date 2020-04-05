package domain

import server.ServerTest

class TimeslotTest : ServerTest() {

//	@Test
//	fun `add timeslots`() = runBlocking {
//		// given
//		val user1 = Users.registerUser(newSimpleUserWithDoctorRole("pera"))
//		val doctor1 = newSimpleDoctor("doc1", user1.id)
//		val timeslot1 = DateTime(date = 20200101, time = 1930)
//		val timeslot2 = DateTime(date = 20200101, time = 2000)
//
//		// when
//		val saved = Doctors.addNewDoctor(doctor1)
//		Doctors.with(saved).bindTimeslots(listOf(timeslot1, timeslot2))
//		val timeslots = Doctors.with(saved).listTimeslots()
//
//		// then
//		assertThat(timeslots.size).isEqualTo(2)
//		assertThat(timeslots).extracting("datetime.value").containsExactly(202001012000L, 202001011930L)
//
//		Unit
//	}


//	@Test
//	fun `add timeslots and ignore duplicates`() = runBlocking {
//		// given
//		val user1 = Users.registerUser(newSimpleUserWithDoctorRole("pera"))
//		val doctor1 = newSimpleDoctor("doc1", user1.id)
//		val timeslot1 = DateTime(date = 20200101, time = 1930)
//		val timeslot2 = DateTime(date = 20200101, time = 2000)
//		val timeslot3 = DateTime(date = 20200101, time = 2030)
//
//		// when
//		val saved = Doctors.addNewDoctor(doctor1)
//		Doctors.with(saved) {
//			it.bindTimeslots(listOf(timeslot1, timeslot2))
//			it.bindTimeslots(listOf(timeslot2, timeslot3))
//		}
//		val timeslots = Doctors.with(saved).listTimeslots()
//
//		// then
//		assertThat(timeslots.size).isEqualTo(3)
//		assertThat(timeslots).extracting("datetime.value")
//			.containsExactly(202001012030, 202001012000, 202001011930)
//
//		Unit
//	}
}
