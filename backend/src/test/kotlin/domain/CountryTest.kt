package domain

import org.junit.jupiter.api.Assertions.*
import common.ServerTest
import kotlinx.coroutines.runBlocking
import model.Country
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CountryTest: ServerTest() {

	@Test
	fun `get all countries` () = runBlocking {
		val countries = Country.getAllValues()

		assertEquals(countries.size, 3)
	}
}

