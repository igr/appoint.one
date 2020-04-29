package server

import domain.doctor.DoctorsTable
import domain.evaluation.EvaluationsTable
import domain.timeslot.TimeslotsTable
import domain.user.UsersTable
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.deleteAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import server.DatabaseFactory.dbtx

open class ServerTest {

	val baseUrl = "http://localhost:8080"

	companion object {
		private var serverStarted = false

		private lateinit var server: ApplicationEngine

		@BeforeAll
		@JvmStatic
		fun startServer() {
			if (!serverStarted) {
				server = embeddedServer(Netty, port = 8080, module = { module(testing = true) })
				server.start()
				serverStarted = true

				Runtime.getRuntime().addShutdownHook(Thread { server.stop(0, 0) })
			}
		}
	}

    @BeforeEach
    fun before() = runBlocking {
        dbtx {
	        EvaluationsTable.deleteAll()
	        TimeslotsTable.deleteAll()
	        DoctorsTable.deleteAll()
	        UsersTable.deleteAll()
        }
        Unit
    }

}
