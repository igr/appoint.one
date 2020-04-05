package domain.country

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

