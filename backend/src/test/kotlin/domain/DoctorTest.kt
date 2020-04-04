package domain

import domain.Users.registerUser
import kotlinx.coroutines.runBlocking
import model.Doctor
import model.newSimpleDoctor
import model.newSimpleUserWithDoctorRole
import model.resetDate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import server.ServerTest

class DoctorTest : ServerTest() {

	@Test
	fun `add doctor`() = runBlocking {
		// given
		val user = registerUser(newSimpleUserWithDoctorRole("pera"))
		val newDoctor = newSimpleDoctor("doc1", user.id)

		// when
		val saved = Doctors.addNewDoctor(newDoctor)

		// then
		val retrieved = Doctors.findById(saved.id)

		val newDoctorCopy = resetDate(Doctor(saved.id, newDoctor.data, saved.user))
		val savedCopy = resetDate(saved)
		val retrievedCopy = resetDate(retrieved)

		assertThat(retrievedCopy?.data).isEqualTo(newDoctorCopy?.data)
		assertThat(retrievedCopy).isEqualTo(savedCopy)

		Unit
	}

    @Test
    fun `find all doctors`() = runBlocking {
	    // given
	    val user1 = registerUser(newSimpleUserWithDoctorRole("pera"))
	    val doctor1 = newSimpleDoctor("doc1", user1.id)
	    val user2 = registerUser(newSimpleUserWithDoctorRole("jelena"))
	    val doctor2 = newSimpleDoctor("doc2", user2.id)

	    Doctors.addNewDoctor(doctor1)
	    Doctors.addNewDoctor(doctor2)

	    // when
	    val doctors = Doctors.listAllDoctors()

	    // then
	    assertThat(doctors).hasSize(2)
	    assertThat(doctors).extracting("data.name").containsExactlyInAnyOrder(doctor1.data.name, doctor2.data.name)

	    Unit
    }
}
