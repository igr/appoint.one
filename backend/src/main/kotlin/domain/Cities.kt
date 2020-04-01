package domain

import model.*
import org.jetbrains.exposed.sql.selectAll
import server.DatabaseFactory.dbtx

@TargetIs("Set of all cities.")
object Cities {

	suspend fun findAllCities(): List<City> = dbtx {
		CitiesRepo.selectAll().toList().map { City(it.get(CitiesRepo.id), it.get(CitiesRepo.name), it.get(CitiesRepo.country)) }
	}
}
