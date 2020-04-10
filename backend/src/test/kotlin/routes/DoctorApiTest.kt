package routes

import domain.doctor.newSimpleDoctorUser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import server.ServerTest

class DoctorApiTest : ServerTest() {

	@Test
	fun `POST doctor`() {
		//given
		val newDoctor = newSimpleDoctorUser("doc1")

		// when
		val created = postDoctor(newDoctor)
		val retrieved = getDoctor(created.id)

		// then
		assertThat(created.data).isEqualTo(retrieved.data)
		assertThat(created.id).isEqualTo(retrieved.id)
	}
}
