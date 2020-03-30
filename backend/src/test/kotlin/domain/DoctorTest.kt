package domain

import kotlinx.coroutines.runBlocking
import model.NewDoctor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import server.ServerTest


class DoctorTest : ServerTest() {

	@Test
	fun `add doctor`() = runBlocking {
		// given
		val new = NewDoctor(name = "doc1")

		// when
		val saved = Doctors.addNewDoctor(new)

		// then
		val retrieved = Doctors.findById(saved.id)

		assertThat(retrieved?.name).isEqualTo(new.name)
		assertThat(retrieved).isEqualTo(saved)

		Unit
	}

    @Test
    fun `find all doctors`() = runBlocking {
	    // given
	    val doctor1 = NewDoctor(name = "doc1")
	    val doctor2 = NewDoctor(name = "doc2")

	    Doctors.addNewDoctor(doctor1)
	    Doctors.addNewDoctor(doctor2)

	    // when
	    val doctors = Doctors.findAllDoctors()

	    // then
	    assertThat(doctors).hasSize(2)
	    assertThat(doctors).extracting("name").containsExactlyInAnyOrder(doctor1.name, doctor2.name)

	    Unit
    }
}
