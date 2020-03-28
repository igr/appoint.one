import java.time.LocalDateTime

data class DateTime(val date: Int, val time: Int)

private fun _pair(dateTime: LocalDateTime): DateTime {
	val date = dateTime.year * 10000 + dateTime.monthValue * 100 + dateTime.dayOfMonth
	val time = dateTime.hour * 100 + dateTime.minute
	return DateTime(date, time)
}

fun LocalDateTime.pair(): DateTime {
	return _pair(this)
}
