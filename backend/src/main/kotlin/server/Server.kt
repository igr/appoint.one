package server

import auth.JwtConfig
import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.mitchellbosecke.pebble.loader.ClasspathLoader
import domain.Users
import io.ktor.application.Application
import io.ktor.application.ApplicationStarted
import io.ktor.application.ApplicationStopped
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.jwt
import io.ktor.features.*
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
import routes.*
import scheduler.Scheduler

private val scheduler = Scheduler(1000)

fun startServer(args: Array<String>) {
	val server = embeddedServer(Netty, commandLineEnvironment(args))
	server.start(wait = true)
}

val Application.envKind get() = environment.config.propertyOrNull("ktor.environment")?.getString()
val Application.isDev get() = envKind == "dev"
val Application.isProd get() = envKind != "dev"

fun Application.module(testing: Boolean = false) {
	install(DefaultHeaders)

	install(Compression)

//	install(CORS) {
//		anyHost()
//	}

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
			disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
			registerKotlinModule()
		}
	}

	install(Authentication) {
		jwt {
			verifier(JwtConfig.verifier)
			realm = JwtConfig.realm
			validate {
				val name = it.payload.getClaim("name")?.asString() ?: return@validate null
				Users.findUserByUsername(name)?.let { user ->
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
		role()
		doctors()
		timeslots()
		data()
	}

	with(environment.monitor) {
		subscribe(ApplicationStarted) {
			DatabaseFactory.init(isDev)
			scheduler.start()
		}
		subscribe(ApplicationStopped) {
			scheduler.stop()
			println("Bye!")
		}
	}

	when {
		isDev -> {
			serverLogger.info("Server is up in DEV")
		}
		isProd -> {
			serverLogger.info("Server is up in PRODUCTION")
		}
	}
}

val serverLogger: Logger = LoggerFactory.getLogger("Server")
