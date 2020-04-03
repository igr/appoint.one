package domain

import model.CitiesRepo
import model.City
import org.jetbrains.exposed.sql.selectAll
import server.DatabaseFactory.dbtx

@TargetIs("Set of all cities.")
object Cities {

	suspend fun findAllCities(): List<City> = dbtx {
		CitiesRepo.selectAll().toList().map {
			City(
				it[CitiesRepo.id],
				it[CitiesRepo.name],
				it[CitiesRepo.country])
		}
	}
}
