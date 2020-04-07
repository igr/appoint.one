package domain.user

import org.jetbrains.exposed.sql.select
import server.DatabaseFactory

class UserById(private val id: UserId) {
	suspend fun get(): User? = DatabaseFactory.dbtx {
		UsersTable.select { UsersTable.id eq id.value }.singleOrNull()?.toUser()
	}

	suspend fun existing(): User? = DatabaseFactory.dbtx {
		UsersTable.select { UsersTable.id eq id.value }.single()?.toUser()
	}
}
