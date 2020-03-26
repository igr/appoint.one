package model

import org.jetbrains.exposed.dao.id.IntIdTable

object CitiesRepo : IntIdTable(name = "cities") {
	val name = varchar("name", 50)
}