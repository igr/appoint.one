package domain.article

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import domain.Id

data class ArticleId @JsonCreator(mode = JsonCreator.Mode.DELEGATING) constructor(
	@JsonValue override val value: Int
) : Id()

fun Int.toArticleId(): ArticleId {
	return ArticleId(this)
}

fun String.toArticleId(): ArticleId {
	return ArticleId(this.toInt())
}

data class Article(
	val id: ArticleId,
	val content: String,
	val title: String
)
