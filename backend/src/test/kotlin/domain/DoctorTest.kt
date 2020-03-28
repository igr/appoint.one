package domain

import common.ServerTest
import kotlinx.coroutines.runBlocking
import model.Country
import model.NewDoctor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class DoctorTest : ServerTest() {

	@Test
	fun `add doctor`() = runBlocking {
		// given
		val new = NewDoctor(name = "doc1", country = Country.SERBIA)

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
	    val doctor1 = NewDoctor(name = "doc1", country = Country.BOSNIA)
	    val doctor2 = NewDoctor(name = "doc2", country = Country.CROATIA)

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
