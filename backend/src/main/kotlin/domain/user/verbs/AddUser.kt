package domain.user.verbs

import auth.BCryptHasher
import domain.user.NewUser
import domain.user.UserId
import domain.user.UsersTable
import domain.user.toUserId
import org.jetbrains.exposed.sql.insertAndGetId

object AddUser : (NewUser) -> UserId {
	override fun invoke(newUser: NewUser): UserId {
		val userId = UsersTable.insertAndGetId {
			it[name] = newUser.name
			it[password] = BCryptHasher.hashPassword(newUser.password)
			it[role] = newUser.role.value
		}
		return userId.toUserId()
	}
}
