package model

import org.jetbrains.exposed.sql.Table

object CitiesTable : Table(name = "cities") {
	val id = integer("id")
	val name = varchar("name", 50)
	val country = integer("country")

	override val primaryKey = PrimaryKey(id)
}
