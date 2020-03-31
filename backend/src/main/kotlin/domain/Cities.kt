package domain

import model.*
import org.jetbrains.exposed.sql.selectAll

@TargetIs("Set of all cities.")
object Cities {

	fun findAllCities(): List<City> {
		val cities = mutableListOf<City>()
		CitiesRepo.selectAll().toList().forEach {
			cities.add(City(it.get(CitiesRepo.id), it.get(CitiesRepo.name), it.get(CitiesRepo.country)))
		}
		return cities;
	}
}
