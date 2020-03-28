package web

import common.ServerTest
import domain.Users
import kotlinx.coroutines.runBlocking
import model.EmailPasswordCredential
import model.User
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class AuthTest : ServerTest() {

	@Test
	fun `login user successfully`() = runBlocking {
		// given
		Users.registerUser(testUser)
		val credential = EmailPasswordCredential("foo@test.com", "pass123")

		// when
		val user = login(credential).extract().to<User>()

		// then
		Assertions.assertThat(user.email).isEqualTo(testUser.email)
		Assertions.assertThat(user.password).isNotEqualTo(testUser.password)
		Assertions.assertThat(user.token).isNotEmpty()

		Unit
	}

}