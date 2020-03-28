package web

import common.ServerTest
import model.Country
import model.Doctor
import model.NewDoctor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DoctorApiTest : ServerTest() {

    @Test
    fun `POST doctor`() {
        val newDoctor = NewDoctor("doc11", Country.SERBIA)

        val created = postDoctor(newDoctor).extract().to<Doctor>()
        val retrieved = getDoctor(created.id).extract().to<Doctor>()

        // then
        assertThat(created.name).isEqualTo(newDoctor.name)
        assertThat(created).isEqualTo(retrieved)
    }

}
