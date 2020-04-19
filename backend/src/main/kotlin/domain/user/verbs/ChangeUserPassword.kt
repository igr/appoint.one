package domain.user.verbs

import auth.BCryptHasher
import domain.user.UserId
import domain.user.UsersTable
import org.jetbrains.exposed.sql.update

object ChangeUserPassword : (UserId, String) -> Boolean {
	override fun invoke(userId: UserId, newPassword: String): Boolean {
		return UsersTable.update({
			UsersTable.id eq userId.value
		}) {
			it[password] = BCryptHasher.hashPassword(newPassword)
		} == 1
	}
}
