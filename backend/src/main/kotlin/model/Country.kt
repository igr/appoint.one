package model

enum class Country(val value: Int) {
	SERBIA(1), BOSNIA(2), CROATIA(3);

	companion object {
		fun of(value: Int): Country = values().find { it.value == value }!!

		fun valuesAsMap(): List<CountryClass> {
			return values().map { CountryClass(it.value, it.name.toLowerCase().capitalize()) }
		}
	}

}

class CountryClass (
	val id: Int,
	val name: String
)

