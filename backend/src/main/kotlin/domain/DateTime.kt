package domain

import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Serializable
data class DateTime(val year: Int, val month: Int, val day: Int, val hour: Int, val minute: Int) {
	constructor(date: Int, time: Int) : this(date / 10_000, (date % 10_000) / 100, (date % 100), time / 100, time % 100)

	constructor(date: Long) : this((date / 10_000).toInt(), (date % 10_000).toInt())

	constructor(date: LocalDateTime) : this(date.year, date.monthValue, date.dayOfMonth, date.hour, date.minute);

	val value: Long = ((((year * 100L + month) * 100 + day) * 100 + hour) * 100) + minute;

	companion object {
		fun now(): DateTime {
			return DateTime(LocalDateTime.now())
		}

		fun ofEpochMilliseconds(epochMillis: Long): LocalDateTime {
			return Instant.ofEpochMilli(epochMillis).atZone(ZoneId.systemDefault()).toLocalDateTime()
		}

		fun ofDate(date: String): DateTime {
			return DateTime((date.replace("-", "") + "0000").toLong())
		}
	}

	fun toLocalDateTime(): LocalDateTime {
		return LocalDateTime.of(year, month, day, hour, minute, 0)
	}
}

fun LocalDateTime.toDateTime(): DateTime {
	return DateTime(this)
}

