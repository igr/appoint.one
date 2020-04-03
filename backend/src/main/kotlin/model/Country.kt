package model

data class Country(
	val id: Int,
	val name: String,
	val prefix: String
) {
	companion object {
		fun of(value: Int): Country {
			return Countries[value] ?: error("Invalid country ID")
		}
	}
}

val Country_SERBIA = Country(1, "Srbija", "+381")
val Country_BOSNIA = Country(2, "Bosna", "+387")
val Country_CROATIA = Country(3, "Hrvatska", "+385")

val Countries = mapOf(
	1 to Country_SERBIA,
	2 to Country_BOSNIA,
	3 to Country_CROATIA
)
