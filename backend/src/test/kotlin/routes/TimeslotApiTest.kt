package routes

import DateTime
import domain.doctor.newSimpleDoctorUser
import domain.doctor.verbs.BindTimeslotsToDoctor
import domain.timeslot.NewTimeslot
import domain.user.verbs.AddUser
import io.ktor.auth.UserPasswordCredential
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import server.DatabaseFactory.dbtx
import server.ServerTest

class TimeslotApiTest : ServerTest() {

	@Test
	fun `POST timeslot`() = runBlocking {
		// given
		AddUser(testUser)
		val credentials = UserPasswordCredential("foo@test.com", "pass123")
		val user = userLogin(credentials)

		val doc1 = postDoctor(newSimpleDoctorUser("pera"))

		val newDoctorTimeslot1 = NewTimeslot(DateTime(20200101, 800), doc1.id)
		val newDoctorTimeslot2 = NewTimeslot(DateTime(20200101, 1000), doc1.id)

		// when
		val retrieved1 = postTimeslot(user.token, newDoctorTimeslot1)
		val retrieved2 = postTimeslot(user.token, newDoctorTimeslot2)

		// then
		assertThat(retrieved1).extracting("datetime.value").containsExactly(202001010800L)
		assertThat(retrieved2).extracting("datetime.value").containsExactly(202001011000L)
	}

	@Test
	fun `GET timeslot`() = runBlocking {
		// given
		val doc1 = postDoctor(newSimpleDoctorUser("Pera"))
		val timeslots = dbtx { BindTimeslotsToDoctor(doc1.id, listOf(DateTime(20200101, 800))) }

		// when
		val timeslot = getTimeslot(timeslots[0])

		// then
		assertThat(timeslot).isNotNull

		Unit
	}

}
