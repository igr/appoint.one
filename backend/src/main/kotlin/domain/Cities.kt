package domain

import model.CitiesTable
import model.City
import org.jetbrains.exposed.sql.selectAll
import server.DatabaseFactory.dbtx

object Cities {

	suspend fun findAllCities(): List<City> = dbtx {
		CitiesTable.selectAll().map {
			City(
				it[CitiesTable.id],
				it[CitiesTable.name],
				it[CitiesTable.country])
		}
	}
}
