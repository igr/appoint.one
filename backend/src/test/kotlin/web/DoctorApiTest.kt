package web

import model.Doctor
import model.newSimpleDoctor
import model.resetDate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import server.ServerTest

class DoctorApiTest : ServerTest() {

    @Test
    fun `POST doctor`() {
	    //given
	    val newDoctor = newSimpleDoctor("doc1")

	    // when
	    val created = postDoctor(newDoctor)
	    val retrieved = getDoctor(created.id)

		val newDoctorCopy = resetDate(Doctor(created.id, newDoctor.doctor, created.user))
		val createdCopy = resetDate(created)

		// then
	    assertThat(createdCopy?.data).isEqualTo(newDoctorCopy?.data)
	    assertThat(created.id).isEqualTo(retrieved.id)
    }

}
