package domain

import kotlinx.coroutines.runBlocking
import model.NewTimeslot
import model.newSimpleDoctor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import server.ServerTest

class TimeslotTest : ServerTest() {

	@Test
	fun `add timeslots`() = runBlocking {
		// given
		val doctor1 = newSimpleDoctor("doc1")
		val timeslot1 = NewTimeslot(date = 20200101, time = 1930)
		val timeslot2 = NewTimeslot(date = 20200101, time = 2000)

		// when
		val saved = Doctors.addNewDoctor(doctor1)
		Doctors.with(saved).bindTimeslots(listOf(timeslot1, timeslot2))
		val timeslots = Doctors.with(saved).listAllTimeslots()

		// then
		assertThat(timeslots.size).isEqualTo(2)
		assertThat(timeslots).extracting("time").containsExactly(1930, 2000)

		Unit
	}


	@Test
	fun `add timeslots and ignore duplicates`() = runBlocking {
		// given
		val doctor1 = newSimpleDoctor("doc1")
		val timeslot1 = NewTimeslot(date = 20200101, time = 1930)
		val timeslot2 = NewTimeslot(date = 20200101, time = 2000)
		val timeslot3 = NewTimeslot(date = 20200101, time = 2030)

		// when
		val saved = Doctors.addNewDoctor(doctor1)
		Doctors.with(saved) {
			it.bindTimeslots(listOf(timeslot1, timeslot2))
			it.bindTimeslots(listOf(timeslot2, timeslot3))
		}
		val timeslots = Doctors.with(saved).listAllTimeslots()

		// then
		assertThat(timeslots.size).isEqualTo(3)
		assertThat(timeslots).extracting("time").containsExactly(1930, 2000, 2030)

		Unit
	}
}
