package web

import io.ktor.auth.UserPasswordCredential
import model.NewUser

val testUser = NewUser(name = "foo@test.com", password = "pass123")
val testUserCredentials = UserPasswordCredential(name = "foo@test.com", password = "pass123")
