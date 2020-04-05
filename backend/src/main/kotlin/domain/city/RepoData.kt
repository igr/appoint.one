package domain.city

import domain.country.Country_BOSNIA
import domain.country.Country_SERBIA
import org.jetbrains.exposed.sql.insertIgnore

val CitiesOfSerbia = listOf(
	"Beograd", "Kraljevo", "Jagodina", "Novi Sad", "Niš",
	"Kragujevac", "Priština", "Subotica", "Zrenjanin", "Pančevo", "Čačak",
	"Kruševac", "Novi Pazar", "Smederevo", "Leskovac", "Užice", "Vranje",
	"Valjevo", "Šabac", "Sombor", "Požarevac", "Pirot", "Zaječar", "Kikinda",
	"Sremska Mitrovica", "Vršac", "Bor", "Prokuplje", "Loznica")
	.mapIndexed { index, cityName ->
		CityModel(index + 1, cityName, Country_SERBIA.id)
	}

val CitiesOfBosnia = listOf(
	"Sarajevo")
	.mapIndexed { index, cityName ->
		CityModel(index + 1001, cityName, Country_BOSNIA.id)
	}

val CitiesOfCroatia = listOf(
	"Zagreb")
	.mapIndexed { index, cityName ->
		CityModel(index + 2001, cityName, Country_BOSNIA.id)
	}


fun storeAllCities() {
	listOf(CitiesOfSerbia, CitiesOfBosnia, CitiesOfCroatia).forEach {
		it.forEach { city ->
			CityTable.insertIgnore { city.data() }
		}
	}
}
