package domain

import DateTime
import domain.doctor.newSimpleDoctorUser
import domain.doctor.newSimpleEvaluationData
import domain.doctor.verbs.BindTimeslotsToDoctor
import domain.doctor.verbs.ListDoctorsTimeslots
import domain.timeslot.verbs.MarkTimeslotAsDone
import domain.timeslot.verbs.ReserveTimeslotIfNew
import domain.user.verbs.AddDoctor
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import server.DatabaseFactory.dbtx
import server.ServerTest

class TimeslotTest : ServerTest() {

	@Test
	fun `add timeslots`() = runBlocking {
		dbtx {
			// given
			val doctorId = AddDoctor(newSimpleDoctorUser("Pera"))
			val timeslot1 = DateTime(date = 20200101, time = 1930)
			val timeslot2 = DateTime(date = 20200101, time = 2000)

			// when
			BindTimeslotsToDoctor(doctorId, listOf(timeslot1, timeslot2))

			// then
			val timeslots = ListDoctorsTimeslots(doctorId)
			assertThat(timeslots.size).isEqualTo(2)
			assertThat(timeslots).extracting("datetime.value").containsExactly(202001012000L, 202001011930L)

			Unit
		}
	}


	@Test
	fun `add timeslots and ignore duplicates`() = runBlocking {
		dbtx {
			// given
			val doctor1Id = AddDoctor(newSimpleDoctorUser("Pera"))
			val timeslot1 = DateTime(date = 20200101, time = 1930)
			val timeslot2 = DateTime(date = 20200101, time = 2000)
			val timeslot3 = DateTime(date = 20200101, time = 2030)

			// when
			BindTimeslotsToDoctor(doctor1Id, listOf(timeslot1, timeslot2, timeslot3))
			BindTimeslotsToDoctor(doctor1Id, listOf(timeslot1, timeslot2, timeslot3))

			// then
			val timeslots = ListDoctorsTimeslots(doctor1Id)

			assertThat(timeslots.size).isEqualTo(3)
			assertThat(timeslots).extracting("datetime.value")
				.containsExactly(202001012030, 202001012000, 202001011930)

			Unit
		}
	}

	@Test
	fun `add timeslot, reserve it and mark it done`() = runBlocking {
		dbtx {
			// given
			val doctor1 = AddDoctor(newSimpleDoctorUser("Pera"))
			val timeslot1 = DateTime(date = 20200101, time = 1930)
			val timeslotId = BindTimeslotsToDoctor(doctor1, listOf(timeslot1))[0]

			ReserveTimeslotIfNew(timeslotId)

			// when
			val evaluationId = MarkTimeslotAsDone(timeslotId, newSimpleEvaluationData())

			// then
			assertThat(evaluationId).isNotNull

			Unit
		}
	}
}
