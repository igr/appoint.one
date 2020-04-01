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
import server.db.loadInitialCities

object DatabaseFactory {

	fun init(isDev: Boolean) {
		Database.connect(hikari())
		transaction {
			SchemaUtils.createMissingTablesAndColumns(
				DoctorsRepo,
				CitiesRepo,
				TimeslotsRepo,
				UsersRepo)

			loadInitialCities()
			if (isDev) {
				createDevData()
			}
		}
    }

    private fun hikari(): HikariDataSource {
	    val dbUser = System.getenv("PH_DATABASE_USER") ?: "phuser"
	    val dbPass = System.getenv("PH_DATABASE_PASS") ?: "phpass1!"

	    val config = HikariConfig("/hikari.properties")
	    config.username = dbUser
	    config.password = dbPass
	    config.validate()

	    return HikariDataSource(config)
    }

    suspend fun <T> dbtx(block: suspend () -> T): T =
        newSuspendedTransaction {
            block()
        }

}
