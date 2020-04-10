package domain.article

class ArticleById(private val articleId: ArticleId) {

	fun get(): Article {
		val content = Article::class.java.getResource("/docs/${articleId.value}.md").readText()
		return Article(articleId, content)
	}
}
