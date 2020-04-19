package domain.user.verbs

import domain.user.User
import domain.user.UsersTable
import domain.user.toUser
import org.jetbrains.exposed.sql.select

object FindUserByUsername : (String) -> User? {
	override fun invoke(username: String): User? {
		return UsersTable
			.select { UsersTable.name eq username }
			.singleOrNull()
			?.toUser()
	}
}
