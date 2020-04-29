package routes

import domain.DateTime
import domain.doctor.newSimpleDoctorUser
import domain.doctor.verbs.BindTimeslotsToDoctor
import domain.user.verbs.AddDoctor
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import server.DatabaseFactory.dbtx
import server.ServerTest
import kotlin.test.assertEquals

class AppointmentApiTest : ServerTest() {

	@Test
	fun `GET appointment`() = runBlocking {
		val tid = dbtx {
			//given
			val doctorId = AddDoctor(newSimpleDoctorUser("doc1"))
			val timeslots = BindTimeslotsToDoctor(doctorId, listOf(DateTime.now()))
			timeslots[0]
		}

		// when
		val (response, appointment) = getAppointment(tid)


		// then
		assertEquals(200, response.status.value)
		assertEquals(tid, appointment.timeslot.id)
		//		assertThat(appointment.).isEqualTo(retrieved.data)
		//		Assertions.assertThat(created.id).isEqualTo(retrieved.id)

		Unit
	}
}
