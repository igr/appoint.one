package domain.timeslot.verbs

import domain.timeslot.TimeslotStatus
import domain.timeslot.TimeslotStatusCount
import domain.timeslot.TimeslotsTable
import domain.timeslot._CalculateTimeslotUsageStats
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.count
import org.jetbrains.exposed.sql.selectAll

object CalculateTimeslotUsageStats : _CalculateTimeslotUsageStats {
	override fun invoke(): List<TimeslotStatusCount> {
		val count = TimeslotsTable.status.count().alias("count")

		return TimeslotsTable
			.slice(TimeslotsTable.status, count)
			.selectAll()
			.groupBy(TimeslotsTable.status)
			.map {
				TimeslotStatusCount(
					TimeslotStatus.of(it[TimeslotsTable.status]),
					it[count]
				)
			}
	}
}
