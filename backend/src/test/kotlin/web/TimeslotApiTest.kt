package web

import model.NewDoctorTimeslots
import model.NewTimeslot
import model.newSimpleDoctor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import server.ServerTest

class TimeslotApiTest : ServerTest() {

	@Test
	fun `POST timeslot`() {
		// given
		val doc1 = postDoctor(newSimpleDoctor("pera"))
		val newDoctorTimeslot = NewDoctorTimeslots(doc1.id, listOf(
			NewTimeslot(20200101, 800),
			NewTimeslot(20200101, 1000)
		))

		// when
		val retrieved = postTimeslot(newDoctorTimeslot)

		// then
		assertThat(retrieved.size).isEqualTo(2)
		assertThat(retrieved).extracting("time").containsExactly(800, 1000)
	}


}
