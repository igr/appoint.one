package routes

import domain.article.verbs.FindExistingArticleById
import id.toArticleId
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import server.DatabaseFactory.dbtx

fun Route.docs() {

	route("/docs") {
		get("/{id}") {
			val articleId = call.parameters["id"]?.toArticleId() ?: throw IllegalStateException("ID missing")

			val article = dbtx {
				FindExistingArticleById(articleId);
			}

			call.respond(article)
		}
	}
}
