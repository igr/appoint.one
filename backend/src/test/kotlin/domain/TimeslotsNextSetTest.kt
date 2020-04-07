package domain

import DateTime
import domain.doctor.DoctorTimeslots
import domain.doctor.newSimpleDoctorUser
import domain.timeslot.TimeslotsNextSet
import domain.user.Users
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import server.ServerTest
import toDateTime
import java.time.LocalDateTime

class TimeslotsNextSetTest : ServerTest() {

	@Test
	fun `find next available timeslot`() = runBlocking {
		// given
		val doctor1 = Users.addDoctor(newSimpleDoctorUser("Pera"))
		val futureTimeslot: DateTime = LocalDateTime.now().plusHours(1).toDateTime()
		val expiredTimeslot: DateTime = LocalDateTime.now().minusDays(1).toDateTime()

		DoctorTimeslots(doctor1).bindTimeslots(listOf(futureTimeslot, expiredTimeslot))

		// when
		val timeslots = TimeslotsNextSet.get()

		// then
		assertThat(timeslots.size).isEqualTo(1)
		assertThat(timeslots[0].timeslot.datetime).isEqualTo(futureTimeslot)
		assertThat(timeslots[0].doctor.id).isEqualTo(doctor1)

		Unit
	}

}
