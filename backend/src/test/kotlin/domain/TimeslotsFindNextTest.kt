package domain

import kotlinx.coroutines.runBlocking
import model.Country
import model.NewDoctor
import model.NewTimeslot
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import server.ServerTest
import java.time.LocalDateTime

class TimeslotsFindNextTest : ServerTest() {

	@Test
	fun `return next available timeslot`() = runBlocking {
		// given
		val doctor1 = Doctors.addNewDoctor(NewDoctor(name = "doc1"))
		val timeslot1 = NewTimeslot(LocalDateTime.now().plusHours(1))
		Doctors.with(doctor1).bindTimeslots(listOf(timeslot1))

		// when
		val timeslots = Timeslots.findNextTimeslots(Country.SERBIA)

		// then
		assertThat(timeslots.size).isEqualTo(1)
		assertThat(timeslots[0].date).isEqualTo(timeslot1.date)
		assertThat(timeslots[0].time).isEqualTo(timeslot1.time)

		Unit
	}

}
