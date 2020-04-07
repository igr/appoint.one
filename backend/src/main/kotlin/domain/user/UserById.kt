package domain.user

import auth.BCryptHasher
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import server.DatabaseFactory.dbtx

class UserById(private val id: UserId) {
	suspend fun get(): User? = dbtx {
		UsersTable.select { UsersTable.id eq id.value }.singleOrNull()?.toUser()
	}

	suspend fun existing(): User? = dbtx {
		UsersTable.select { UsersTable.id eq id.value }.single().toUser()
	}

	suspend fun changePassword(newPassword: String) = dbtx {
		UsersTable.update({
			UsersTable.id eq id.value
		}) {
			it[password] = BCryptHasher.hashPassword(newPassword)
		}
	}

}
