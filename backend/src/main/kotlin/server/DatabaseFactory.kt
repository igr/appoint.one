package server

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import domain.city.CityTable
import domain.city.storeAllCities
import domain.doctor.DoctorsTable
import domain.evaluation.EvaluationsTable
import domain.timeslot.TimeslotsTable
import domain.user.UsersTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import server.db.createDevData

object DatabaseFactory {

	fun init(isDev: Boolean) {
		Database.connect(hikari())
		transaction {
			SchemaUtils.createMissingTablesAndColumns(
				DoctorsTable,
				CityTable,
				TimeslotsTable,
				UsersTable,
				EvaluationsTable
			)

			storeAllCities()

			// todo TEMPORARY DISABLED!
			//if (isDev) {
			createDevData()
			//}
		}
    }

    private fun hikari(): HikariDataSource {
	    val config = HikariConfig("/hikari.properties")
	    if (System.getenv("DATABASE_DB") != null) {
		    val databaseDb = System.getenv("DATABASE_DB")
		    config.jdbcUrl = "jdbc:postgresql://$databaseDb"
	    }
	    if (System.getenv("DATABASE_USER") != null) {
		    config.username = System.getenv("DATABASE_USER")
	    }
	    if (System.getenv("DATABASE_PASS") != null) {
		    config.password = System.getenv("DATABASE_PASS")
	    }

	    config.validate()

	    serverLogger.info("Connecting to: ${config.jdbcUrl}")

	    return HikariDataSource(config)
    }

    suspend fun <T> dbtx(block: suspend () -> T): T =
        newSuspendedTransaction {
            block()
        }

}
