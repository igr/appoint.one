package model

import org.jetbrains.exposed.sql.Table

object CitiesRepo : Table(name = "cities") {
	val id = integer("id")
	val name = varchar("name", 50)
	val country = integer("country")

	override val primaryKey = PrimaryKey(id, name = "id")
}

class City (
	cityId: Int,
	cityCountry: Int
) {
	val id: Int
	val country: Country

	init {
		this.id = cityId
		country = Country.of(cityCountry)
	}
}