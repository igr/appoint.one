package domain.doctor

import domain.user.AccessDenied
import domain.user.UserId
import domain.user.UsersTable
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import server.DatabaseFactory.dbtx

class DoctorById(private val id: DoctorId) {

	suspend fun get(): Doctor? = dbtx {
		DoctorsTable.select { DoctorsTable.id eq id.value }.singleOrNull()?.toDoctor()
	}

	suspend fun existing(): Doctor = dbtx {
		DoctorsTable.select { DoctorsTable.id eq id.value }.single().toDoctor()
	}

	suspend fun update(doctorData: DoctorData) = dbtx {
		DoctorsTable.update({
			DoctorsTable.id eq id.value
		}) {
			doctorData.data(it)
		}

		UsersTable.update({
			UsersTable.id eq id.value
		}) {
			it[name] = doctorData.email
		}
	}

	fun assertUser(userId: UserId?): DoctorById {
		if (userId == null) {
			throw AccessDenied()
		}
		if (id.value != userId.value) {
			throw AccessDenied()
		}
		return this
	}

}
