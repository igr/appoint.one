package model

import org.jetbrains.exposed.sql.Table

object CitiesRepo : Table(name = "cities") {
	val id = integer("id")
	val name = varchar("name", 50)

	override val primaryKey = PrimaryKey(id, name = "id")
}
