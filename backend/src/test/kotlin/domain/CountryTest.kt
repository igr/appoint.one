package domain

import kotlinx.coroutines.runBlocking
import model.Country
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import server.ServerTest

class CountryTest: ServerTest() {

	@Test
	fun `get all countries` () = runBlocking {
		val countries = Country.values()

		assertEquals(countries.size, 3)
	}
}

