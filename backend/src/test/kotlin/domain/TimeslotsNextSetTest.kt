package domain

import domain.doctor.newSimpleDoctorUser
import domain.doctor.verbs.BindTimeslotsToDoctor
import domain.timeslot.verbs.DetermineNextAvailableTimeslots
import domain.user.verbs.AddDoctor
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import server.DatabaseFactory.dbtx
import server.ServerTest
import java.time.LocalDateTime

class TimeslotsNextSetTest : ServerTest() {

	@Test
	fun `find next available timeslot`() = runBlocking {
		dbtx {
			// given
			val doctorId = AddDoctor(newSimpleDoctorUser("Pera"))
			val futureTimeslot: DateTime = LocalDateTime.now().plusHours(1).toDateTime()
			val expiredTimeslot: DateTime = LocalDateTime.now().minusDays(1).toDateTime()

			BindTimeslotsToDoctor(doctorId, listOf(futureTimeslot, expiredTimeslot))

			// when
			val timeslots = DetermineNextAvailableTimeslots(5, DateTime.now())

			// then
			assertThat(timeslots.size).isEqualTo(1)
			assertThat(timeslots[0].timeslot.datetime).isEqualTo(futureTimeslot)
			assertThat(timeslots[0].doctor.id).isEqualTo(doctorId)

			Unit
		}
	}

}
