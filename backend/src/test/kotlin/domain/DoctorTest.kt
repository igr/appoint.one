package domain

import server.ServerTest

class DoctorTest : ServerTest() {

//	@Test
//	fun `add doctor`() = runBlocking {
//		// given
//		val newDoctor = newSimpleDoctor("pera")
//
//		// when
//		val userId = Users.addNewDoctorUser(newDoctor)
//
//
//		// then
//		val saved = Doctors.findExistingByUserId(userId)
//
//		val newDoctorCopy = saved.resetDate()
//
//		assertThat(retrievedCopy?.data).isEqualTo(newDoctorCopy?.data)
//		assertThat(retrievedCopy).isEqualTo(savedCopy)
//
//		Unit
//	}

//    @Test
//    fun `find all doctors`() = runBlocking {
//	    // given
//	    val user1 = registerUser(newSimpleUserWithDoctorRole("pera"))
//	    val doctor1 = newSimpleDoctor("doc1", user1.id)
//	    val user2 = registerUser(newSimpleUserWithDoctorRole("jelena"))
//	    val doctor2 = newSimpleDoctor("doc2", user2.id)
//
//	    Doctors.addNewDoctor(doctor1)
//	    Doctors.addNewDoctor(doctor2)
//
//	    // when
//	    val doctors = Doctors.listAllDoctors()
//
//	    // then
//	    assertThat(doctors).hasSize(2)
//	    assertThat(doctors).extracting("data.name").containsExactlyInAnyOrder(doctor1.data.name, doctor2.data.name)
//
//	    Unit
//    }
}
