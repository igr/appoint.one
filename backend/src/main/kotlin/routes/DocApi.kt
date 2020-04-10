package routes

import domain.article.ArticleById
import domain.article.toArticleId
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route

fun Route.docs() {

	route("/docs") {
		get("/{id}") {
			val id = call.parameters["id"]?.toArticleId() ?: throw IllegalStateException("ID missing")
			call.respond(ArticleById(id).get())
		}
	}
}
