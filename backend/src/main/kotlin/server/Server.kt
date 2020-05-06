package server

import auth.JwtConfig
import com.mitchellbosecke.pebble.loader.ClasspathLoader
import domain.user.verbs.FindUserById
import id.toUserId
import io.ktor.application.*
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.jwt
import io.ktor.features.*
import io.ktor.http.ContentType
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.pebble.Pebble
import io.ktor.routing.HttpMethodRouteSelector
import io.ktor.routing.Route
import io.ktor.routing.Routing
import io.ktor.serialization.DefaultJsonConfiguration
import io.ktor.serialization.json
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.serialization.json.Json
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.event.Level
import routes.*
import scheduler.Scheduler
import server.DatabaseFactory.dbtx

fun startServer(args: Array<String>) {
	val server = embeddedServer(Netty, commandLineEnvironment(args))
	server.start(wait = true)
}

val Application.envKind get() = environment.config.propertyOrNull("ktor.environment")?.getString()
val Application.isDev get() = envKind == "dev"
val Application.isProd get() = envKind != "dev"

fun Application.module(testing: Boolean = false) {
	if (testing) {
		serverLogger.info("TEST MODE")
	}

	install(DefaultHeaders)

	install(Compression) {
		gzip()
	}

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
		json(
			contentType = ContentType.Application.Json,
			json = Json(
				DefaultJsonConfiguration.copy(
					prettyPrint = true
				)
			)
		)
	}

	install(Authentication) {
		jwt {
			verifier(JwtConfig.verifier)
			realm = JwtConfig.realm
			validate {
				val userId = it.payload.getClaim("id")?.asInt()?.toUserId() ?: return@validate null
				dbtx {
					FindUserById(userId)?.let { user ->
						val token = JwtConfig.makeToken(user)
						user.copy(token = token)
					}
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
		users()
		timeslots()
		appointment()
		evaluations()
		docs()
		stats()
		mails()
	}

	with(environment.monitor) {
		subscribe(ApplicationStarted) {
			DatabaseFactory.init(isDev)
			Scheduler.start(1000)
		}
		subscribe(ApplicationStopped) {
			Scheduler.stop()
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

	// log routes
	val root = feature(Routing)
	val allRoutes = allRoutes(root)
	val allRoutesWithMethod = allRoutes.filter { it.selector is HttpMethodRouteSelector }.sortedBy { it.toString() }
	allRoutesWithMethod.forEach {
		serverLogger.info("route: $it")
	}

}

fun allRoutes(root: Route): List<Route> {
	return listOf(root) + root.children.flatMap { allRoutes(it) }
}

val serverLogger: Logger = LoggerFactory.getLogger("Server")
