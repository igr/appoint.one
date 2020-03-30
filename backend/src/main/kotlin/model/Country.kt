package model

enum class Country(val value: Int) {
	SERBIA(1), BOSNIA(2), CROATIA(3);

	companion object {
		fun of(value: Int): Country = values().find { it.value == value }!!

		fun getAllValues(): MutableMap<Int, String> {
			val mappedValues = mutableMapOf<Int, String>()
			values().forEach {
				mappedValues.put(it.value, it.toString())
			}
			return mappedValues
		}
	}

}
