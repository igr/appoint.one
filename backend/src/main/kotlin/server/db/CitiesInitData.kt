package server.db

import model.CitiesTable
import model.Country_BOSNIA
import model.Country_CROATIA
import model.Country_SERBIA
import org.jetbrains.exposed.sql.insertIgnore

fun loadInitialCitiesOfSerbia() {
	listOf(
		"Beograd", "Kraljevo", "Jagodina", "Novi Sad", "Niš",
		"Kragujevac", "Priština", "Subotica", "Zrenjanin", "Pančevo", "Čačak",
		"Kruševac", "Novi Pazar", "Smederevo", "Leskovac", "Užice", "Vranje",
		"Valjevo", "Šabac", "Sombor", "Požarevac", "Pirot", "Zaječar", "Kikinda",
		"Sremska Mitrovica", "Vršac", "Bor", "Prokuplje", "Loznica")
		.forEachIndexed { index, cityName ->
			CitiesTable.insertIgnore {
				it[id] = index + 1
				it[name] = cityName
				it[country] = Country_SERBIA.id
			}
		}
}

fun loadInitialCitiesOfBosnia() {
	listOf("Sarajevo")
		.forEachIndexed { index, cityName ->
			CitiesTable.insertIgnore {
				it[id] = index + 1001
				it[name] = cityName
				it[country] = Country_BOSNIA.id
			}
		}
}

fun loadInitialCitiesOfCroatia() {
	listOf("Zagreb")
		.forEachIndexed { index, cityName ->
			CitiesTable.insertIgnore {
				it[id] = index + 2001
				it[name] = cityName
				it[country] = Country_CROATIA.id
			}
		}
}
