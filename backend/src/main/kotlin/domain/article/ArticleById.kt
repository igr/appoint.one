package domain.article

import io.ktor.http.HttpStatusCode
import server.StatusException

class ArticleById(private val articleId: ArticleId) {

	fun get(): Article {
		val content = Article::class.java.getResource("/docs/${articleId.value}.md").readText()

		var title = "";
		when (articleId.value) {
			1 -> title = "BESPLATNA PSIHOTERAPIJSKA PODRŠKA U KRIZI COVID-19"
			else -> throw StatusException("Article not found.", HttpStatusCode.NotFound)
		}

		return Article(articleId, content, title)
	}
}
