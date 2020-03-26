package model

import org.jetbrains.exposed.sql.Table

object Requests : Table() {
	val id = integer("id").autoIncrement()
	val email = varchar("email", 64)
	val phone = varchar("phone", 32)
	val name = varchar("name", 255).nullable()
	// city

	override val primaryKey = PrimaryKey(id)
}

data class Request(
	val id: Int,
	val name: String,
	val confirmed: Boolean,
	val dateUpdated: Long
)
