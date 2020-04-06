package domain.doctor

import org.jetbrains.exposed.sql.select
import server.DatabaseFactory

class DoctorById(private val id: DoctorId) {

	suspend fun get(): Doctor? = DatabaseFactory.dbtx {
		DoctorsTable.select { DoctorsTable.id eq id.value }.singleOrNull()?.toDoctor()
	}

	suspend fun existing(): Doctor = DatabaseFactory.dbtx {
		DoctorsTable.select { DoctorsTable.id eq id.value }.single().toDoctor()
	}

}
