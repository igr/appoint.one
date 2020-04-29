package domain.user.verbs

import domain.user.User
import domain.user.UsersTable
import domain.user.toUser
import id.UserId
import org.jetbrains.exposed.sql.select

object FindUserById : (UserId) -> User? {
	override fun invoke(userId: UserId): User? {
		return UsersTable
			.select { UsersTable.id eq userId.value }
			.singleOrNull()
			?.toUser()
	}
}
