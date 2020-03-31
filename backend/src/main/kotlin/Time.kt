import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
data class DateTime(val date: Int, val time: Int) {

	fun equalsTo(date: Int, time: Int): Boolean {
		return this.date == date && this.time == time
	}

	constructor(date: Long) : this((date / 10_000).toInt(), (date % 10_000).toInt())

	val value: Long
		get() = date * 10_000L + time;
}

fun LocalDateTime.toDateTime(): DateTime {
	val date = this.year * 10000 + this.monthValue * 100 + this.dayOfMonth
	val time = this.hour * 100 + this.minute
	return DateTime(date, time)
}

fun LocalDateTime.toDateTimeLong(): Long {
	val date = this.year * 10000 + this.monthValue * 100 + this.dayOfMonth
	val time = this.hour * 100 + this.minute
	return date * 10_000L + time
}
