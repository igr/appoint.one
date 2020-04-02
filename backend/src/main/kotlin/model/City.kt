package model

import org.jetbrains.exposed.sql.Table

object CitiesRepo : Table(name = "cities") {
	val id = integer("id")
	val name = varchar("name", 50)
	val country = integer("country")

	override val primaryKey = PrimaryKey(id, name = "id")
}

/* MODEL */

class City(
	val id: Int,
	val name: String,
	val country: Int
)
