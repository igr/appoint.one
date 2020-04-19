package domain

import domain.doctor.newSimpleDoctorUser
import domain.doctor.verbs.FindExistingDoctorById
import domain.doctor.verbs.ListDoctorsOrdered
import domain.user.verbs.AddDoctor
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import server.DatabaseFactory.dbtx
import server.ServerTest

class DoctorTest : ServerTest() {

	@Test
	fun `add doctor`() = runBlocking {
		dbtx {
			// given
			val newDoctor = newSimpleDoctorUser("Pera")

			// when
			val doctorId = AddDoctor(newDoctor)

			// then
			val savedDoctor = FindExistingDoctorById(doctorId)
			assertThat(savedDoctor.data).isEqualTo(newDoctor.doctor)

			Unit
		}
	}

	@Test
	fun `find all doctors`() = runBlocking {
		dbtx {
			// given
			val doctor1 = newSimpleDoctorUser("Pera")
			val doctor2 = newSimpleDoctorUser("Jelena")

			AddDoctor(doctor1)
			AddDoctor(doctor2)

			// when
			val doctors = ListDoctorsOrdered();

			// then
			assertThat(doctors).hasSize(2)
			assertThat(doctors).extracting("data.name")
				.containsExactlyInAnyOrder(doctor1.doctor.name, doctor2.doctor.name)

			Unit
		}
	}
}
