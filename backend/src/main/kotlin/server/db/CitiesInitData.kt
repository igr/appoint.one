package server.db

import model.CitiesRepo
import model.Country
import org.jetbrains.exposed.sql.insertIgnore

fun loadInitialCities() {
    listOf("Beograd", "Kraljevo", "Jagodina", "Novi Sad", "Niš",
            "Kragujevac", "Priština", "Subotica", "Zrenjanin", "Pančevo", "Čačak",
            "Kruševac", "Novi Pazar", "Smederevo", "Leskovac", "Užice", "Vranje",
            "Valjevo", "Šabac", "Sombor", "Požarevac", "Pirot", "Zaječar", "Kikinda",
            "Sremska Mitrovica", "Vršac", "Bor", "Prokuplje", "Loznica")
        .forEachIndexed { index, cityName ->
            CitiesRepo.insertIgnore {
                it[id] = index + 1
                it[name] = cityName
				it[country] = Country.SERBIA.value
            }
        }
}
