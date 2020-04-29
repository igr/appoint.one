package routes

import auth.LoginCredential
import domain.user.NewUser

val testUser = NewUser(name = "foo@test.com", password = "pass123")
val testUserCredentials = LoginCredential(name = "foo@test.com", password = "pass123")
