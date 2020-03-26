package domain

import infra.DatabaseFactory.dbtx
import model.*
import org.jetbrains.exposed.sql.deleteAll

object Invitations {

	suspend fun add(request: NewInvitation): Invitation = dbtx {
		val saved = InvitationEntity.new {
			name = request.name
			email = request.email
			phone = request.phone
		}
		findExisting(saved.id.value).toInvitation()
	}

	suspend fun findById(id: Int): Invitation? = dbtx {
		InvitationEntity.findById(id)?.toInvitation()
	}

	suspend fun deleteAll() = dbtx {
		TimeslotsRepo.deleteAll();
	}

	private fun findExisting(id: Int): InvitationEntity {
		return InvitationEntity.find { InvitationRepo.id eq id }.single()
	}

}
