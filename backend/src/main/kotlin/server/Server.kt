package server

import auth.JwtConfig
import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.mitchellbosecke.pebble.loader.ClasspathLoader
import domain.Users
import io.ktor.application.Application
import io.ktor.application.ApplicationStarted
import io.ktor.application.ApplicationStopped
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.jwt
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.jackson.jackson
import io.ktor.pebble.Pebble
import io.ktor.routing.Routing
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.event.Level
import scheduler.Scheduler
import server.routes.auth
import server.routes.doctors
import server.routes.index
import server.routes.timeslots

private val scheduler = Scheduler(1000)

private fun start() {
	DatabaseFactory.init()
	scheduler.start()
}

private fun end() {
	scheduler.stop()
}

fun startServer(args: Array<String>) {
	val server = embeddedServer(Netty, commandLineEnvironment(args))
	server.start(wait = true)
}


fun Application.module(testing: Boolean = false) {
	install(DefaultHeaders)

	install(StatusPages) {
		setup()
	}

	install(CallLogging) {
		level = Level.DEBUG
	}

	install(ContentNegotiation) {
		jackson {
			registerModule(JavaTimeModule())
			setDefaultPrettyPrinter(DefaultPrettyPrinter().apply {
				indentArraysWith(DefaultPrettyPrinter.FixedSpaceIndenter.instance)
				indentObjectsWith(DefaultIndenter("  ", "\n"))
			})
		}
	}

	install(Authentication) {
		jwt {
			verifier(JwtConfig.verifier)
			realm = JwtConfig.realm
			validate {
				val email = it.payload.getClaim("email")?.asString() ?: return@validate null
				Users.findUserByEmail(email)?.let { user ->
					val token = JwtConfig.makeToken(user)
					user.copy(token = token)
				}
			}
		}
	}
	install(Pebble) {
		loader(ClasspathLoader().apply {
			prefix = "templates"
		})
	}

	install(Routing) {
		static("assets") {
			resources("assets")
		}
		index()
		auth()
		doctors()
		timeslots()
	}

	with(environment.monitor) {
		subscribe(ApplicationStarted) {
			println("Hi!")
			start()
		}
		subscribe(ApplicationStopped) {
			end()
			println("Bye!")
		}
	}
}

val serverLogger: Logger = LoggerFactory.getLogger("Server")
