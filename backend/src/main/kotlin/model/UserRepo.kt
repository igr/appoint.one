package model

import auth.BCryptHasher
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object UsersRepo : IntIdTable(name = "users") {
	val name = varchar("name", 128).uniqueIndex()
	val password = varchar("password", length = 255)
	val role = integer("role")
}

class UserEntity(id: EntityID<Int>) : Entity<Int>(id) {
	companion object : EntityClass<Int, UserEntity>(UsersRepo)

	var name by UsersRepo.name
	var password by UsersRepo.password
	var role by UsersRepo.role

	fun toUser(): User {
		return User(
			id = UserId(id.value),
			password = password,
			name = name,
			role = UserRole.of(role)
		)
	}
}

fun UserEntity.Companion.findById(userId: UserId): UserEntity? {
	return UserEntity.findById(userId.value)
}

fun UserEntity.Companion.add(user: NewUser): UserEntity {
	return UserEntity.new {
		name = user.name
		password = BCryptHasher.hashPassword(user.password)
		role = user.role.value
	}
}
