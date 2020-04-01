package domain

import kotlinx.coroutines.runBlocking
import model.Country
import model.NewTimeslot
import model.newSimpleDoctor
import model.newSimpleUserWithDoctorRole
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import server.ServerTest
import toDateTime
import java.time.LocalDateTime

class TimeslotsFindNextTest : ServerTest() {

	@Test
	fun `find next available timeslot`() = runBlocking {
		// given
		val user1 = Users.registerUser(newSimpleUserWithDoctorRole("pera"))
		val doctor1 = Doctors.addNewDoctor(newSimpleDoctor("doc1", user1.id))
		val futureTimeslot: NewTimeslot = LocalDateTime.now().plusHours(1).toDateTime()
		val expiredTimeslot: NewTimeslot = LocalDateTime.now().minusDays(1).toDateTime()
		Doctors.with(doctor1).bindTimeslots(listOf(futureTimeslot, expiredTimeslot))

		// when
		val timeslots = Timeslots.findNextTimeslots(Country.SERBIA)

		// then
		assertThat(timeslots.size).isEqualTo(1)
		assertThat(timeslots[0].datetime).isEqualTo(futureTimeslot)
		assertThat(timeslots[0].doctor.id).isEqualTo(doctor1.id)

		Unit
	}

}
