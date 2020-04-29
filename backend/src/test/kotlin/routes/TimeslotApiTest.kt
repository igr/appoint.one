package routes

import domain.DateTime
import domain.doctor.newSimpleDoctorUser
import domain.doctor.verbs.BindTimeslotsToDoctor
import domain.timeslot.NewTimeslot
import domain.timeslot.verbs.FindExistingTimeslotById
import domain.user.verbs.AddUser
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import server.DatabaseFactory.dbtx
import server.ServerTest

class TimeslotApiTest : ServerTest() {

	@Test
	fun `POST timeslot`() = runBlocking {
		// given
		dbtx { AddUser(testUser) }
		val credentials = testUserCredentials
		val (_, user) = userLogin(credentials)

		val (_, doc1) = postDoctor(newSimpleDoctorUser("pera"))

		val newDoctorTimeslot1 = NewTimeslot(DateTime(20200101, 800), doc1.id)
		val newDoctorTimeslot2 = NewTimeslot(DateTime(20200101, 1000), doc1.id)

		// when
		val (_, retrieved1) = postTimeslot(user.token, newDoctorTimeslot1)
		val (_, retrieved2) = postTimeslot(user.token, newDoctorTimeslot2)

		// then
		val ts1 = dbtx { FindExistingTimeslotById(retrieved1[0]) }
		assertThat(ts1.datetime.value).isEqualTo(202001010800L)
		val ts2 = dbtx { FindExistingTimeslotById(retrieved2[0]) }
		assertThat(ts2.datetime.value).isEqualTo(202001011000L)

		Unit
	}

	@Test
	fun `GET timeslot`() = runBlocking {
		// given
		val (_, doc1) = postDoctor(newSimpleDoctorUser("Pera"))
		val timeslots = dbtx { BindTimeslotsToDoctor(doc1.id, listOf(DateTime(20200101, 800))) }

		// when
		val timeslot = getTimeslot(timeslots[0])

		// then
		assertThat(timeslot).isNotNull

		Unit
	}

}
