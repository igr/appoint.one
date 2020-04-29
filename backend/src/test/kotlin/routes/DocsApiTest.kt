package routes

import domain.article.Article
import id.toArticleId
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readText
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import server.ServerTest
import server.json
import kotlin.test.assertEquals

class DocsApiTest : ServerTest() {

	@Test
	fun `GET article`() = runBlocking {
		//given
		val articleId = 1.toArticleId()

		// when
		val response = httpClient.get<HttpResponse>("${baseUrl}/docs/${articleId.value}")
		val article = json.parse(Article.serializer(), response.readText())


		// then
		assertEquals(200, response.status.value)
		assertEquals(articleId, article.id)

		Unit
	}
}
