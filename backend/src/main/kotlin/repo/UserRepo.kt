package repo

import auth.BCryptHasher
import model.NewUser
import model.User
import model.UserRole
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.InsertStatement

object UsersTable : IntIdTable(name = "users") {
	val name = varchar("name", 128).uniqueIndex()
	val password = varchar("password", length = 255)
	val role = integer("role")
}


fun ResultRow.toUser() = User(
	id = this[UsersTable.id].value,
	name = this[UsersTable.name],
	password = this[UsersTable.password],
	role = UserRole.of(this[UsersTable.role]),
	token = ""
)


fun NewUser.data(): UsersTable.(InsertStatement<Number>) -> Unit {
	return {
		it[name] = this@data.name
		it[password] = BCryptHasher.hashPassword(this@data.password)
		it[role] = this@data.role.value
	}
}
