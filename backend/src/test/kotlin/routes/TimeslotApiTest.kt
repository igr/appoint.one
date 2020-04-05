package routes

import DateTime
import domain.doctor.DoctorTimeslots
import domain.doctor.newSimpleDoctorUser
import domain.timeslot.NewTimeslot
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import server.ServerTest

class TimeslotApiTest : ServerTest() {

	@Test
	fun `POST timeslot`() {
		// given
		val doc1 = postDoctor(newSimpleDoctorUser("pera"))

		val newDoctorTimeslot1 = NewTimeslot(DateTime(20200101, 800), doc1.id)
		val newDoctorTimeslot2 = NewTimeslot(DateTime(20200101, 1000), doc1.id)

		// when
		val retrieved1 = postTimeslot(newDoctorTimeslot1)
		val retrieved2 = postTimeslot(newDoctorTimeslot2)

		// then
		assertThat(retrieved1).extracting("datetime.value").containsExactly(202001010800L)
		assertThat(retrieved2).extracting("datetime.value").containsExactly(202001011000L)
	}

	@Test
	fun `GET timeslot`() = runBlocking {
		// given
		val doc1 = postDoctor(newSimpleDoctorUser("Pera"))
		val timeslots = DoctorTimeslots(doc1.id).bindTimeslots(
			listOf(DateTime(20200101, 800)));

		// when
		val timeslot = getTimeslot(timeslots[0])

		// then
		assertThat(timeslot).isNotNull

		Unit
	}

}
