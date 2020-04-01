package web

import model.newSimpleDoctor
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
	    // then
	    assertThat(created.data).isEqualToIgnoringGivenFields(newDoctor.doctor, "dateUpdated")
	    assertThat(created).isEqualTo(retrieved)
    }

}
