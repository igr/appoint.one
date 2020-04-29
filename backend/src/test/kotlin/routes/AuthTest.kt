package routes

import auth.LoginCredential
import domain.user.verbs.AddUser
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import server.DatabaseFactory.dbtx
import server.ServerTest

class AuthTest : ServerTest() {

	@Test
	fun `login user successfully`() = runBlocking {
		// given
		dbtx { AddUser(testUser) }
		val credentials = testUserCredentials

		// when
		val (response, user) = userLogin(credentials)

		// then
		assertThat(response.status.value).isEqualTo(200)
		assertThat(user.name).isEqualTo(testUser.name)
		assertThat(user.password).isEmpty()
		assertThat(user.token).isNotEmpty()     // kotlin issue, must use the method

		Unit
	}

	@Test
	fun `can not login with wrong password`() = runBlocking {
		// given
		dbtx { AddUser(testUser) }
		val credentials = LoginCredential("foo@test.com", "wrongPass")

		val response = userLoginResponse(credentials)

		assertThat(response.status.value).isEqualTo(401)

		Unit
	}

	@Test
	fun `can not access user when not logged in`() = runBlocking {
		val (response, _) = userGet()

		assertThat(response.status.value).isEqualTo(401)

		Unit
	}

	@Test
	fun `get user info when logged in`() = runBlocking {
		// given
		dbtx { AddUser(testUser) }

		// when
		val (_, loggedUser) = userLogin(testUserCredentials)
		val (_, remoteUser) = userGet(loggedUser.token)

		// then
		assertThat(remoteUser.name).isEqualTo(loggedUser.name)

		Unit
	}

}
