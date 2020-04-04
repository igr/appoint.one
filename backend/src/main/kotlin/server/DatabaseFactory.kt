package server

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import model.CitiesRepo
import model.DoctorsRepo
import model.TimeslotsRepo
import model.UsersRepo
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import server.db.createDevData
import server.db.loadInitialCitiesOfBosnia
import server.db.loadInitialCitiesOfCroatia
import server.db.loadInitialCitiesOfSerbia

object DatabaseFactory {

	fun init(isDev: Boolean) {
		Database.connect(hikari())
		transaction {
			SchemaUtils.createMissingTablesAndColumns(
				DoctorsRepo,
				CitiesRepo,
				TimeslotsRepo,
				UsersRepo)

			loadInitialCitiesOfSerbia()
			loadInitialCitiesOfBosnia()
			loadInitialCitiesOfCroatia()

			if (isDev) {
				createDevData()
			}
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
