package server

import domain.city.CityTable
import domain.doctor.DoctorsTable
import domain.evaluation.EvaluationsTable
import domain.timeslot.TimeslotsTable
import domain.user.UsersTable
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.restassured.RestAssured
import io.restassured.parsing.Parser
import io.restassured.response.ResponseBodyExtractionOptions
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.deleteAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import server.DatabaseFactory.dbtx

open class ServerTest {

// using rest-assured extension, so we don't need this
//    protected fun RequestSpecification.When(): RequestSpecification {
//        return this.`when`()
//    }

    inline fun <reified T> ResponseBodyExtractionOptions.to(): T {
        return this.`as`(T::class.java)
    }

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

                RestAssured.baseURI = "http://localhost"
                RestAssured.port = 8080
                RestAssured.defaultParser = Parser.JSON;

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
	        CityTable.deleteAll()
        }
        Unit
    }

}
