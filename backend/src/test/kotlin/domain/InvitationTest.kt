package domain

import common.ServerTest
import kotlinx.coroutines.runBlocking
import model.NewInvitation
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class InvitationTest : ServerTest() {

    @Test
    fun `add invitation`() = runBlocking {
        // given
        val new = NewInvitation(name = "inv1", email = "email1@email.com", phone = "phone1")

        // when
        val saved = Invitations.addNewInvitation(new)

        // then
        val retrieved = Invitations.findInvitationById(saved.id)

        Assertions.assertThat(retrieved?.name).isEqualTo(new.name)
        Assertions.assertThat(retrieved?.email).isEqualTo(new.email)
        Assertions.assertThat(retrieved?.phone).isEqualTo(new.phone)
        Assertions.assertThat(retrieved).isEqualTo(saved)

        Unit
    }
}
