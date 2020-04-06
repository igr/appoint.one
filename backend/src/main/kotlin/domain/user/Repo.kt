package domain.user

import auth.BCryptHasher
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.InsertStatement

object UsersTable : IntIdTable(name = "users") {
	val name = varchar("name", 128).uniqueIndex()
	val password = varchar("password", length = 255)
	val role = integer("role")
}

fun ResultRow.toUser() = User(
	id = this[UsersTable.id].toUserId(),
	name = this[UsersTable.name],
	password = this[UsersTable.password],
	role = UserRole.of(this[UsersTable.role]),
	token = ""
)

fun NewUser.data(insert: InsertStatement<EntityID<Int>>) {
	val obj = this
	with(UsersTable) {
		insert[name] = obj.name
		insert[password] = BCryptHasher.hashPassword(obj.password)
		insert[role] = obj.role.value
	}
}
