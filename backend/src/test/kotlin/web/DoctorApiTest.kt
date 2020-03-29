package web

import common.ServerTest
import model.NewDoctor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DoctorApiTest : ServerTest() {

    @Test
    fun `POST doctor`() {
        //given
        val newDoctor = NewDoctor("doc11")

        // when
        val created = postDoctor(newDoctor)
        val retrieved = getDoctor(created.id)
        // then
        assertThat(created.name).isEqualTo(newDoctor.name)
        assertThat(created).isEqualTo(retrieved)
    }

}
