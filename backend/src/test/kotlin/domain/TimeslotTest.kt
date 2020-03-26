package domain

import common.ServerTest
import kotlinx.coroutines.runBlocking
import model.NewDoctor
import model.NewTimeslot
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TimeslotTest : ServerTest() {

	@Test
	fun `add timeslots`() = runBlocking {
		// given
		val doctor1 = NewDoctor(name = "doc1")
		val timeslot1 = NewTimeslot(date = 20200101, time = 1930)
		val timeslot2 = NewTimeslot(date = 20200101, time = 2000)

		// when
		val saved = Doctors.add(doctor1)
		Doctors.with(saved).bindTimeslots(listOf(timeslot1, timeslot2))
		val timeslots = Doctors.with(saved).listTimeslots()

		// then
		assertThat(timeslots.size).isEqualTo(2)
		assertThat(timeslots).extracting("time").containsExactly(1930, 2000)

		Unit
	}

}