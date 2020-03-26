import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.mitchellbosecke.pebble.loader.ClasspathLoader
import io.ktor.application.Application
import io.ktor.application.ApplicationStarted
import io.ktor.application.ApplicationStopped
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.jackson.jackson
import io.ktor.pebble.Pebble
import io.ktor.routing.Routing
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import web.doctor
import web.index
import web.timeslots

fun Application.module(testing: Boolean = false) {
	install(DefaultHeaders)
	install(CallLogging)
	install(ContentNegotiation) {
		jackson {
			registerModule(JavaTimeModule())
			setDefaultPrettyPrinter(DefaultPrettyPrinter().apply {
				indentArraysWith(DefaultPrettyPrinter.FixedSpaceIndenter.instance)
				indentObjectsWith(DefaultIndenter("  ", "\n"))
			})
		}
	}
	install(Pebble) {
		loader(ClasspathLoader().apply {
			prefix = "templates"
		})
	}
	environment.monitor.subscribe(ApplicationStarted){
		println("My app is ready to roll")
	}
	environment.monitor.subscribe(ApplicationStopped){
		println("Time to clean up")
		App.end()
	}

	App.start()

	install(Routing) {
		static("assets") {
			resources("assets")
		}
		index()
		doctor()
		timeslots()
	}

}

fun main(args: Array<String>) {
	embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
}