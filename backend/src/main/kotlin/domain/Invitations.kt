package domain

import infra.DatabaseFactory.dbtx
import model.*
import org.jetbrains.exposed.sql.deleteAll

@Target("Set of all invitations.")
object Invitations {

	/**
	 * Adds new invitation.
	 */
	suspend fun addNewInvitation(request: NewInvitation): Invitation = dbtx {
		val saved = InvitationEntity.new {
			name = request.name
			email = request.email
			phone = request.phone
		}
		findExisting(saved.id.value).toInvitation()
	}

	/**
	 * Finds invitation by its ID.
	 */
	suspend fun findInvitationById(id: Int): Invitation? = dbtx {
		InvitationEntity.findById(id)?.toInvitation()
	}

	/**
	 * Removes all invitations from DB.
	 */
	suspend fun deleteAllInvitations() = dbtx {
		TimeslotsRepo.deleteAll();
	}

	private fun findExisting(id: Int): InvitationEntity {
		return InvitationEntity.find { InvitationRepo.id eq id }.single()
	}

}
