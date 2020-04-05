package domain.doctor

import org.jetbrains.exposed.sql.update
import server.DatabaseFactory

/**
 * Operations that assumes that doctor is know. Returned classes
 * DO NOT have doctor with it.
 */
class DoctorEnabler(private val doctorId: Int) {

	suspend fun confirmDoctor() = DatabaseFactory.dbtx {
		DoctorsTable.update({ DoctorsTable.id eq doctorId }) {
			it[confirmed] = true
		}
	}

	suspend fun unconfirmDoctor() = DatabaseFactory.dbtx {
		DoctorsTable.update({ DoctorsTable.id eq doctorId }) {
			it[confirmed] = false
		}
	}

}
