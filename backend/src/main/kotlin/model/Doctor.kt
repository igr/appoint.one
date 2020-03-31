package model

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object DoctorsRepo : IntIdTable(name = "doctors") {
	val name = varchar("name", 255)
	val country = integer("country")
	val confirmed = bool("confirmed")
	val dateUpdated = long("dateUpdated").clientDefault { System.currentTimeMillis() }
}

class DoctorEntity(id: EntityID<Int>) : Entity<Int>(id) {
	companion object : EntityClass<Int, DoctorEntity>(DoctorsRepo)

	var name by DoctorsRepo.name
	var confirmed by DoctorsRepo.confirmed
	var country by DoctorsRepo.country
	var dateUpdated by DoctorsRepo.dateUpdated

	val timeslots by TimeslotEntity referrersOn TimeslotsRepo.doctor

	fun toDoctor() = Doctor(
		id = DoctorId(id.value),
		name = name,
		country = Country.of(country),
		confirmed = confirmed,
		dateUpdated = Instant.ofEpochMilli(dateUpdated).atZone(ZoneId.systemDefault()).toLocalDateTime()
	)
}

fun DoctorEntity.Companion.findExisting(id: Int): DoctorEntity {
	return find { DoctorsRepo.id eq id }.single()
}

/* DOMAIN */

data class DoctorId(
	val value: Int
)

data class Doctor(
	val id: DoctorId,
	val name: String,
	val country: Country,
	val confirmed: Boolean,
	val dateUpdated: LocalDateTime
)

data class NewDoctor(
	val name: String,
	val country: Country = Country.SERBIA
)
