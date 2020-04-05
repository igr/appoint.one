package web

import server.ServerTest

class TimeslotApiTest : ServerTest() {

//	@Test
//	fun `POST timeslot`() {
//		// given
//		val doc1 = postDoctor(newSimpleDoctor("pera"))
//		val newDoctorTimeslot1 = NewTimeslot(doc1.id, DateTime(20200101, 800))
//		val newDoctorTimeslot2 = NewTimeslot(doc1.id, DateTime(20200101, 1000))
//
//		// when
//		val retrieved1 = postTimeslot(newDoctorTimeslot1)
//		val retrieved2 = postTimeslot(newDoctorTimeslot2)
//
//		// then
//		assertThat(retrieved1).extracting("datetime.value").containsExactly(202001010800L)
//		assertThat(retrieved2).extracting("datetime.value").containsExactly(202001011000L)
//	}

//	@Test
//	fun `GET timeslot`() = runBlocking {
//		// given
//		val doc1 = postDoctor(newSimpleDoctor("pera"))
//		val timeslots = Doctors.with(doc1).bindTimeslots(listOf(
//			DateTime(20200101, 800)
//		));
//
//		// when
//		val timeslot = getTimeslot(timeslots[0].id)
//
//		// then
//		assertThat(timeslot).isNotNull
//
//		Unit
//	}

}
