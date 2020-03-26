package domain

import infra.DatabaseFactory
import model.*

object Requests {

	fun addNewRequest() {
	}


	suspend fun add(request: NewRequest): Request = DatabaseFactory.dbtx {
		val saved = RequestEntity.new {
			name = request.name
			email = request.email
			phone = request.phone
		}
		Requests.findExisting(saved.id.value).toRequest()
	}

	private fun findExisting(id: Int): RequestEntity {
		return RequestEntity.find { RequestsRepo.id eq id }.single()
	}


	suspend fun findById(id: Int): Request? = DatabaseFactory.dbtx {
		RequestEntity.findById(id)?.toRequest()
	}



}
