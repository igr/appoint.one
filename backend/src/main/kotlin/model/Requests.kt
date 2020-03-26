package model

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object RequestsRepo : IntIdTable() {
	val name = varchar("name", 255).nullable()
	val email = varchar("email", 64)
	val phone = varchar("phone", 32)
	// city
}

class RequestEntity(id: EntityID<Int>) : Entity<Int>(id) {
	companion object : EntityClass<Int, RequestEntity>(RequestsRepo)

	var name by RequestsRepo.name
	var email by RequestsRepo.email
	var phone by RequestsRepo.phone

	fun toRequest() = Request(
			id = id.value,
			name = name,
			email = email,
			phone = phone
			// confirmed = confirmed,
			// dateUpdated = Instant.ofEpochMilli(dateUpdated).atZone(ZoneId.systemDefault()).toLocalDateTime()
	)
}

data class Request(
	val id: Int,
	val name: String?,
	val email: String,
	val phone: String
	// ,
	// val confirmed: Boolean,
	// val dateUpdated: Long
)

data class NewRequest(
		val name: String,
		val email: String,
		val phone: String
)
