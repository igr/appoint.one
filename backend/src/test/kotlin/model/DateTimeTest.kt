package model

import DateTime
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import toDateTime
import java.time.LocalDateTime

class DateTimeTest {

	@Test
	fun `convert from local date time`() {
		val dt = LocalDateTime.of(2020, 3, 7, 10, 30, 50).toDateTime();

		assertEquals(2020, dt.year)
		assertEquals(3, dt.month)
		assertEquals(7, dt.day)
		assertEquals(10, dt.hour)
		assertEquals(30, dt.minute)
		assertEquals(202003071030L, dt.value)
	}

	@Test
	fun `convert from long`() {
		val dt = DateTime(202003071020)

		assertEquals(2020, dt.year)
		assertEquals(3, dt.month)
		assertEquals(7, dt.day)
		assertEquals(10, dt.hour)
		assertEquals(20, dt.minute)
		assertEquals(202003071020, dt.value)
	}
}
