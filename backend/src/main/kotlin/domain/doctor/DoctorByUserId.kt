package domain.doctor

import org.jetbrains.exposed.sql.select
import server.DatabaseFactory

class DoctorByUserId(private val userId: Int) {

	suspend fun get(): Doctor? = DatabaseFactory.dbtx {
		DoctorsTable
			.select { DoctorsTable.userId eq userId }
			.singleOrNull()?.toDoctor()
	}

	suspend fun existing(): Doctor = DatabaseFactory.dbtx {
		DoctorsTable
			.select { DoctorsTable.userId eq userId }
			.single().toDoctor()
	}

}
