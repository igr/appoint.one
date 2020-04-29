package domain.article

import appoint1.annotations.GENERATED
import appoint1.annotations.IdGen
import domain.Id
import id.ArticleId
import kotlinx.serialization.Serializable

@IdGen
val _ArticleId: Id = GENERATED()

@Serializable
data class Article(
	val id: ArticleId,
	val content: String,
	val title: String
)
