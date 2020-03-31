package model

import DateTime
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import toDateTime
import java.time.LocalDateTime

class DateTimeTest {

	@Test
	fun `convert from local date time`() {
		val dt = LocalDateTime.of(2020, 3, 7, 10, 20, 0).toDateTime();

		assertEquals(20200307, dt.date)
		assertEquals(1020, dt.time)
		assertEquals(202003071020L, dt.value)
	}

	@Test
	fun `convert from long`() {
		val dt = DateTime(202003071020)

		assertEquals(20200307, dt.date)
		assertEquals(1020, dt.time)
		assertEquals(202003071020, dt.value)
	}
}
