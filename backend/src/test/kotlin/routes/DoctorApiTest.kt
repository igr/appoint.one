package routes

import domain.doctor.newSimpleDoctorUser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import server.ServerTest

class DoctorApiTest : ServerTest() {

	@Test
	fun `GET appointment`() {
		//given
		val newDoctor = newSimpleDoctorUser("doc1")


		// when
		val (_, created) = postDoctor(newDoctor)
		val (_, retrieved) = getDoctor(created.id)

		// then
		assertThat(created.data).isEqualTo(retrieved.data)
		assertThat(created.id).isEqualTo(retrieved.id)

		Unit
	}
}
