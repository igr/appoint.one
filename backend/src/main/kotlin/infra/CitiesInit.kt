package infra

import model.CitiesRepo
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

object CitiesInit {

    fun init() {

        val cities = listOf<String>("Beograd", "Kraljevo", "Jagodina")

        val citiesExist = (cities.map { it to false}).toMutableSet()
        val citiesFromTable = CitiesRepo.selectAll()
        citiesFromTable.forEach {
            citiesExist.remove(Pair(it[CitiesRepo.name], false))
            citiesExist.add(Pair(it[CitiesRepo.name], true))
        }

        citiesExist.forEach {
            if (!it.second) {
                val cityName = it.first
                CitiesRepo.insert {
                    it[name] = cityName
                }
            }
        }
    }
}
