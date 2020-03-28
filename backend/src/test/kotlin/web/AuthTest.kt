package web

import common.ServerTest
import domain.Users
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import kotlinx.coroutines.runBlocking
import model.EmailPasswordCredential
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AuthTest : ServerTest() {

	@Test
	fun `login user successfully`() = runBlocking {
		// given
		Users.registerUser(testUser)
		val credential = EmailPasswordCredential("foo@test.com", "pass123")

		// when
		val user = userLogin(credential)

		// then
		assertThat(user.email).isEqualTo(testUser.email)
		assertThat(user.password).isNotEqualTo(testUser.password)
		assertThat(user.token).isNotEmpty()

		Unit
	}

	@Test
	fun `can not access user when not logged in`() = runBlocking {
		Given {
			contentType(ContentType.JSON)
		} When {
			get("/user")
		} Then {
			statusCode(401)
		}
		Unit
	}


	@Test
	fun `get user info when logged in`() = runBlocking {
		// given
		Users.registerUser(testUser)

		// when
		val loggedUser = userLogin(testUserCredentials)
		val remoteUser = user(loggedUser.token)

		// then
		assertThat(remoteUser.email).isEqualTo(loggedUser.email)

		Unit
	}

}