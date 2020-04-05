package domain

import domain.country.Countries
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import server.ServerTest

class CountryTest: ServerTest() {

	@Test
	fun `get all countries`() {
		// given
		val countries = Countries

		// then
		assertEquals(countries.size, 3)
	}
}

