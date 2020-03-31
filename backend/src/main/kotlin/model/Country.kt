package model

enum class Country(val value: Int) {
	SERBIA(1), BOSNIA(2), CROATIA(3);

	companion object {
		fun of(value: Int): Country = values().find { it.value == value }!!

		fun valuesAsMap(): Map<Int, String> {
			return values().map { it.value to it.name }.toMap()
		}
	}

}
