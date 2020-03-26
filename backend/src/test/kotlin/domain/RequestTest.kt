package domain

import common.ServerTest
import kotlinx.coroutines.runBlocking
import model.NewRequest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class RequestTest: ServerTest() {

    @Test
    fun `add Request`() = runBlocking {
        // given
        val new = NewRequest(name = "request1", email = "email1@email.com", phone = "phone1")

        // when
        val saved = Requests.add(new)

        // then
        val retrieved = Requests.findById(saved.id)

        Assertions.assertThat(retrieved?.name).isEqualTo(new.name)
        Assertions.assertThat(retrieved?.email).isEqualTo(new.email)
        Assertions.assertThat(retrieved?.phone).isEqualTo(new.phone)
        Assertions.assertThat(retrieved).isEqualTo(saved)

        Unit
    }
}
