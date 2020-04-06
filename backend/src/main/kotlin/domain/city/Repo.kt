package domain.city

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.UpdateBuilder

object CityTable : Table(name = "cities") {
	val id = integer("id")
	val name = varchar("name", 50)
	val countryId = integer("country")

	override val primaryKey = PrimaryKey(id)
}

fun ResultRow.toCity() = City(
	id = this[CityTable.id].toCityId(),
	name = this[CityTable.name],
	countryId = this[CityTable.countryId]
)

fun City.data(insert: UpdateBuilder<*>) {
	val obj = this
	with(CityTable) {
		insert[id] = obj.id.value
		insert[name] = obj.name
		insert[countryId] = obj.countryId
	}
}
