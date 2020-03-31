package domain

import model.Country
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import server.ServerTest

class CountryTest: ServerTest() {

	@Test
	fun `get all countries`() {
		// given
		val countries = Country.values()

		// then
		assertEquals(countries.size, 3)
	}
}

