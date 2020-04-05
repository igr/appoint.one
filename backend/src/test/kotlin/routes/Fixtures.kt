package routes

import domain.user.NewUser
import io.ktor.auth.UserPasswordCredential

val testUser = NewUser(name = "foo@test.com", password = "pass123")
val testUserCredentials = UserPasswordCredential(name = "foo@test.com", password = "pass123")
