package common

import domain.Doctors
import domain.Invitations
import domain.Timeslots
import infra.DatabaseFactory.dbtx
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.restassured.RestAssured
import io.restassured.parsing.Parser
import io.restassured.response.ResponseBodyExtractionOptions
import kotlinx.coroutines.runBlocking
import module
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach

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
            Invitations.deleteAll()
            Timeslots.deleteAll()
            Doctors.deleteAll()
        }
        Unit
    }

}
