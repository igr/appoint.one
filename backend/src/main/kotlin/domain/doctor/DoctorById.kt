package domain.doctor

import org.jetbrains.exposed.sql.select
import server.DatabaseFactory

class DoctorById(private val id: Int) {

	suspend fun get(): Doctor? = DatabaseFactory.dbtx {
		DoctorsTable.select { DoctorsTable.id eq id }.singleOrNull()?.toDoctor()
	}

	suspend fun existing(): Doctor = DatabaseFactory.dbtx {
		DoctorsTable.select { DoctorsTable.id eq id }.single().toDoctor()
	}

}
