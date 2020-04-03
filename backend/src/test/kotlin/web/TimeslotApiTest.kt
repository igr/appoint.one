package web

import DateTime
import domain.Doctors
import kotlinx.coroutines.runBlocking
import model.NewDoctorTimeslot
import model.newSimpleDoctor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import server.ServerTest

class TimeslotApiTest : ServerTest() {

	@Test
	fun `POST timeslot`() {
		// given
		val doc1 = postDoctor(newSimpleDoctor("pera"))
		val newDoctorTimeslot1 = NewDoctorTimeslot(doc1.id, DateTime(20200101, 800))
		val newDoctorTimeslot2 = NewDoctorTimeslot(doc1.id, DateTime(20200101, 1000))

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
		val doc1 = postDoctor(newSimpleDoctor("pera"))
		val timeslots = Doctors.with(doc1).bindTimeslots(listOf(
			DateTime(20200101, 800)
		));

		// when
		val timeslot = getTimeslot(timeslots[0].id)

		// then
		assertThat(timeslot).isNotNull

		Unit
	}

}
