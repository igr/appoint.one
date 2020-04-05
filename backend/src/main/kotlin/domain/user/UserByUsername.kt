package domain.user

import org.jetbrains.exposed.sql.select
import server.DatabaseFactory

class UserByUsername(private val name: String) {

	suspend fun get(): User? = DatabaseFactory.dbtx {
		UsersTable.select { UsersTable.name eq name }.singleOrNull()?.toUser()
	}

}
