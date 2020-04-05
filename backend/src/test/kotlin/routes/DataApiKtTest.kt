package routes

import domain.country.Countries
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import server.ServerTest

class DataApiKtTest: ServerTest() {

	@Test
	fun `get all cities`() = runBlocking {
		// when
		val cities = getCities()

		// then
		assertThat(cities).isNotEmpty
	}


	@Test
	fun `get all countries`() = runBlocking {
		// when
		val countries = getCountries()

		// then
		assertThat(countries.size).isEqualTo(Countries.size)
	}

}
