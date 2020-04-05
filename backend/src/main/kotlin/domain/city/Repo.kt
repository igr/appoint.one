package domain.city

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.InsertStatement

object CityTable : Table(name = "cities") {
	val id = integer("id")
	val name = varchar("name", 50)
	val countryId = integer("country")

	override val primaryKey = PrimaryKey(id)
}

fun ResultRow.toCity() = CityModel(
	id = this[CityTable.id],
	name = this[CityTable.name],
	countryId = this[CityTable.countryId]
)

fun CityModel.data(): CityTable.(InsertStatement<Number>) -> Unit {
	return {
		it[id] = this@data.id
		it[name] = this@data.name
		it[countryId] = this@data.countryId
	}
}
