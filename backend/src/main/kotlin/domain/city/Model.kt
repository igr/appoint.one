package domain.city

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import domain.Id
import org.jetbrains.exposed.dao.id.EntityID

data class CityId @JsonCreator(mode = JsonCreator.Mode.DELEGATING) constructor(
	@JsonValue override val value: Int
) : Id()

fun Int.toCityId(): CityId {
	return CityId(this)
}

fun String.toCityId(): CityId {
	return CityId(this.toInt())
}

fun EntityID<Int>.toCityId(): CityId {
	return CityId(this.value);
}


class City(
	val id: CityId,
	val name: String,
	val countryId: Int
)
