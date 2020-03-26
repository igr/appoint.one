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
	val confirmed = bool("confirmed")
	val dateUpdated = long("dateUpdated").clientDefault{ System.currentTimeMillis() }

	override val primaryKey = PrimaryKey(id)
}

class DoctorEntity(id: EntityID<Int>) : Entity<Int>(id) {
	companion object : EntityClass<Int, DoctorEntity>(DoctorsRepo)

	var name by DoctorsRepo.name
	var confirmed by DoctorsRepo.confirmed
	var dateUpdated by DoctorsRepo.dateUpdated

	fun toDoctor() = Doctor(
		id = id.value,
		name = name,
		confirmed = confirmed,
		dateUpdated = Instant.ofEpochMilli(dateUpdated).atZone(ZoneId.systemDefault()).toLocalDateTime()
	)
}

data class Doctor(
	val id: Int,
	val name: String,
	val confirmed: Boolean,
	val dateUpdated: LocalDateTime
)

data class NewDoctor(
	val id: Int? = null,
	val name: String
)
