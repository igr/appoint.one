package server.db

import domain.Users
import kotlinx.coroutines.runBlocking
import model.NewUser
import model.UserRole

fun createDevAdmin() {
	runBlocking {
		Users.registerUser(NewUser("admin", "admin", UserRole.ADMIN));
	}
}
