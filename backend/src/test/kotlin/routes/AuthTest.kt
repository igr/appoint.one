package routes

import domain.user.verbs.AddUser
import io.ktor.auth.UserPasswordCredential
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
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
		val credentials = UserPasswordCredential("foo@test.com", "pass123")

		// when
		val user = userLogin(credentials)

		// then
		assertThat(user.name).isEqualTo(testUser.name)
		assertThat(user.password).isEmpty()
		assertThat(user.token).isNotEmpty()     // kotlin issue, must use the method

		Unit
	}

	@Test
	fun `can not login with wrong password`() = runBlocking {
		// given
		dbtx { AddUser(testUser) }
		val credentials = UserPasswordCredential("foo@test.com", "wrongPass")

		Given {
			body(credentials)
			contentType(ContentType.JSON)
		} When {
			post("/login")
		} Then {
			statusCode(401)
		}

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
		dbtx { AddUser(testUser) }

		// when
		val loggedUser = userLogin(testUserCredentials)
		val remoteUser = userGet(loggedUser.token)

		// then
		assertThat(remoteUser.name).isEqualTo(loggedUser.name)

		Unit
	}

}
