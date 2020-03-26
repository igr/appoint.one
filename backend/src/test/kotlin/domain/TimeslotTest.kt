package domain

import common.ServerTest
import kotlinx.coroutines.runBlocking
import model.NewDoctor
import org.junit.jupiter.api.Test

class TimeslotTest : ServerTest() {

	@Test
	fun `list timeslots`() = runBlocking {
		// given
		val doctor1 = NewDoctor(name = "doc1")

		// when
		val saved = Doctors.add(doctor1)


		// then
		val timeslots = Doctors.with(saved).listTimeslots()

		Unit
	}

}