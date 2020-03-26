package domain

import common.ServerTest
import kotlinx.coroutines.runBlocking
import model.NewDoctor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class DoctorTest : ServerTest() {

	@Test
	fun `add doctor`() = runBlocking {
		// given
		val doctor1 = NewDoctor(name = "doc1")

		// when
		val saved = Doctors.add(doctor1)

		// then
		val retrieved = Doctors.findById(saved.id)
		assertEquals(retrieved, saved)
		assertThat(retrieved?.name).isEqualTo(doctor1.name)

		Unit
	}

    @Test
    fun `find all doctors`() = runBlocking {
	    // given
	    val doctor1 = NewDoctor(name = "doc1")
	    val doctor2 = NewDoctor(name = "doc2")

	    Doctors.add(doctor1)
	    Doctors.add(doctor2)

	    // when
	    val doctors = Doctors.findAll()

	    // then
	    assertThat(doctors).hasSize(2)
	    assertThat(doctors).extracting("name").containsExactlyInAnyOrder(doctor1.name, doctor2.name)

	    Unit
    }
}