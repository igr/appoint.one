package model

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object InvitationRepo : IntIdTable(name = "invitations") {
	val name = varchar("name", 255).nullable()
	val email = varchar("email", 64)
	val phone = varchar("phone", 32)
	// city
}

class InvitationEntity(id: EntityID<Int>) : Entity<Int>(id) {
	companion object : EntityClass<Int, InvitationEntity>(InvitationRepo)

	var name by InvitationRepo.name
	var email by InvitationRepo.email
	var phone by InvitationRepo.phone

	fun toInvitation() = Invitation(
		id = id.value,
		name = name,
		email = email,
		phone = phone
		// confirmed = confirmed,
		// dateUpdated = Instant.ofEpochMilli(dateUpdated).atZone(ZoneId.systemDefault()).toLocalDateTime()
	)
}

data class Invitation(
	val id: Int,
	val name: String?,
	val email: String,
	val phone: String
	// ,
	// val confirmed: Boolean,
	// val dateUpdated: Long
)

data class NewInvitation(
	val name: String,
	val email: String,
	val phone: String
)
