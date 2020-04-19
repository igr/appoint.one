package domain.article.verbs

import domain.article.Article
import domain.article.ArticleId
import io.ktor.http.HttpStatusCode
import server.StatusException

object FindExistingArticleById : (ArticleId) -> Article {
	override fun invoke(articleId: ArticleId): Article {
		val content = Article::class.java.getResource("/docs/${articleId.value}.md").readText()

		val title: String =
			when (articleId.value) {
				1 -> "BESPLATNA PSIHOTERAPIJSKA PODRŠKA U KRIZI COVID-19"
				else -> throw StatusException("Article not found.", HttpStatusCode.NotFound)
			}

		return Article(articleId, content, title)
	}
}
