package model

data class Country(
	val id: Int,
	val name: String
) {
	companion object {
		fun of(value: Int): Country {
			return Countries[value] ?: error("Invalid country ID")
		}
	}
}

val Country_SERBIA = Country(1, "Serbia")
val Country_BOSNIA = Country(2, "Bosna")
val Country_CROATIA = Country(3, "Croatia")

val Countries = mapOf(
	1 to Country(1, "Serbia"),
	2 to Country(2, "Bosna"),
	3 to Country(3, "Hrvatska")
)
