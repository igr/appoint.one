package domain

import DateTime
import domain.doctor.DoctorTimeslots
import domain.doctor.newSimpleDoctorUser
import domain.doctor.newSimpleEvaluationData
import domain.timeslot.TimeslotById
import domain.user.Users
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import server.ServerTest

class TimeslotTest : ServerTest() {

	@Test
	fun `add timeslots`() = runBlocking {
		// given
		val doctorId = Users.addDoctor(newSimpleDoctorUser("Pera"))
		val timeslot1 = DateTime(date = 20200101, time = 1930)
		val timeslot2 = DateTime(date = 20200101, time = 2000)

		// when
		DoctorTimeslots(doctorId).bindTimeslots(listOf(timeslot1, timeslot2))

		// then
		val timeslots = DoctorTimeslots(doctorId).listTimeslots()
		assertThat(timeslots.size).isEqualTo(2)
		assertThat(timeslots).extracting("datetime.value").containsExactly(202001012000L, 202001011930L)

		Unit
	}


	@Test
	fun `add timeslots and ignore duplicates`() = runBlocking {
		// given
		val doctor1 = Users.addDoctor(newSimpleDoctorUser("Pera"))
		val timeslot1 = DateTime(date = 20200101, time = 1930)
		val timeslot2 = DateTime(date = 20200101, time = 2000)
		val timeslot3 = DateTime(date = 20200101, time = 2030)

		// when
		DoctorTimeslots(doctor1).bindTimeslots(listOf(timeslot1, timeslot2, timeslot3))
		DoctorTimeslots(doctor1).bindTimeslots(listOf(timeslot1, timeslot2, timeslot3))

		// then
		val timeslots = DoctorTimeslots(doctor1).listTimeslots()

		assertThat(timeslots.size).isEqualTo(3)
		assertThat(timeslots).extracting("datetime.value")
			.containsExactly(202001012030, 202001012000, 202001011930)

		Unit
	}

	@Test
	fun `add timeslot, reserve it and mark it done`() = runBlocking {
		// given
		val doctor1 = Users.addDoctor(newSimpleDoctorUser("Pera"))
		val timeslot1 = DateTime(date = 20200101, time = 1930)
		val timeslotId = DoctorTimeslots(doctor1).bindTimeslots(listOf(timeslot1))[0]
		TimeslotById(timeslotId).reserveIfNew()

		// when
		val evaluationId = TimeslotById(timeslotId).markDone(newSimpleEvaluationData())

		// then
		assertThat(evaluationId).isNotNull

		Unit
	}
}
