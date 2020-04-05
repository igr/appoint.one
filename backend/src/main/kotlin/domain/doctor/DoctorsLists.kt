package domain.doctor

import org.jetbrains.exposed.sql.selectAll
import server.DatabaseFactory

object DoctorsLists {

	/**
	 * Returns all doctors, ordered by ID (added time)
	 */
	suspend fun allDoctorsOrdered(): List<Doctor> = DatabaseFactory.dbtx {
		DoctorsTable.selectAll().sortedBy { DoctorsTable.id }.toList().map { it.toDoctor() }
	}

}
