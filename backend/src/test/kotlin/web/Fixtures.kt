package web

import model.EmailPasswordCredential
import model.NewUser

val testUser = NewUser(email = "foo@test.com", password = "pass123")
val testUserCredentials = EmailPasswordCredential(email = "foo@test.com", password = "pass123")