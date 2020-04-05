package domain

import DateTime
import model.NewTimeslot
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import repo.DoctorsTable
import repo.TimeslotsTable
import repo.data
import repo.toTimeslot
import server.DatabaseFactory.dbtx

/**
 * Operations that assumes that doctor is know. Returned classes
 * DO NOT have doctor with it.
 */
class DoctorUnit internal constructor(private val doctorId: Int) {

	suspend fun listTimeslots() = dbtx {
		TimeslotsTable.select {
			TimeslotsTable.doctorId eq doctorId
		}
			.sortedWith(compareBy { it[TimeslotsTable.datetime] })
			.reversed()
			.map { it.toTimeslot() }
	}

	suspend fun bindTimeslots(dateTimeList: List<DateTime>) = dbtx {
		val existingTimeslots = listTimeslots()

		dateTimeList
			.filter {
				existingTimeslots.none { existing ->
					existing.datetime == it
				}
			}
			.map {
				NewTimeslot(datetime = it, doctorId = doctorId)
			}
			.forEach { newTimeslot ->
				TimeslotsTable.insert {
					newTimeslot.data()
				}
			}
	}

	suspend fun enable(confirmed: Boolean) = dbtx {
		DoctorsTable.update({ DoctorsTable.id eq doctorId }) {
			it[DoctorsTable.confirmed] = confirmed
		}
	}

}
