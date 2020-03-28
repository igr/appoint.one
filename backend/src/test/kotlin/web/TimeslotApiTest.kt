package web

import common.ServerTest
import model.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TimeslotApiTest : ServerTest() {

	@Test
	fun `POST timeslot`() {
		val doc1 = postDoctor(NewDoctor("Hrvoje", country = Country.CROATIA)).extract().to<Doctor>()
		val newDoctorTimeslot = NewDoctorTimeslots(doc1.id, listOf(
			NewTimeslot(20200101, 800),
			NewTimeslot(20200101, 1000)
		))

		// when
		val retrieved = postTimeslot(newDoctorTimeslot).extract().to<List<Timeslot>>()

		// then
		assertThat(retrieved.size).isEqualTo(2)
		assertThat(retrieved).extracting("time").containsExactly(800, 1000)
	}


}
