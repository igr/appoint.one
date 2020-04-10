package domain

import domain.doctor.DoctorByUserId
import domain.doctor.DoctorsLists
import domain.doctor.newSimpleDoctorUser
import domain.user.Users
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import server.ServerTest

class DoctorTest : ServerTest() {

	@Test
	fun `add doctor`() = runBlocking {
		// given
		val newDoctor = newSimpleDoctorUser("Pera")

		// when
		val userId = Users.addDoctor(newDoctor)

		// then
		val savedDoctor = DoctorByUserId(userId.value).existing()
		assertThat(savedDoctor.data).isEqualTo(newDoctor.doctor)

		Unit
	}

	@Test
	fun `find all doctors`() = runBlocking {
		// given
		val doctor1 = newSimpleDoctorUser("Pera")
		val doctor2 = newSimpleDoctorUser("Jelena")

		Users.addDoctor(doctor1)
		Users.addDoctor(doctor2)

		// when
		val doctors = DoctorsLists.allDoctorsOrdered()

		// then
		assertThat(doctors).hasSize(2)
		assertThat(doctors).extracting("data.name")
			.containsExactlyInAnyOrder(doctor1.doctor.name, doctor2.doctor.name)

		Unit
	}
}
