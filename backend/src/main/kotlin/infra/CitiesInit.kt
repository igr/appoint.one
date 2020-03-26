package infra

import model.CitiesRepo
import org.jetbrains.exposed.sql.insertIgnore

fun loadInitialCities() {
    listOf("Beograd", "Kraljevo", "Jagodina")
        .forEachIndexed { index, cityName ->
            CitiesRepo.insertIgnore {
                it[id] = index + 1
                it[name] = cityName
            }
        }
}
