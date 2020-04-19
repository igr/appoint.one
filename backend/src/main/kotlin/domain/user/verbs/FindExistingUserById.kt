package domain.user.verbs

import domain.user.User
import domain.user.UserId
import domain.user.UsersTable
import domain.user.toUser
import org.jetbrains.exposed.sql.select

object FindExistingUserById : (UserId) -> User {
	override fun invoke(userId: UserId): User {
		return UsersTable.select { UsersTable.id eq userId.value }
			.single()
			.toUser()
	}
}
