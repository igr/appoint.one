package model

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

enum class Country(val value: Int) {
    SERBIA(1),
    BOSNIA(2),
    CROATIA(3)
}

object DoctorsRepo : IntIdTable(name = "doctors") {
    val name = varchar("name", 255)
    val confirmed = bool("confirmed")
    val country = integer("country")
    val dateUpdated = long("dateUpdated").clientDefault{ System.currentTimeMillis() }
}

class DoctorEntity(id: EntityID<Int>) : Entity<Int>(id) {
    companion object : EntityClass<Int, DoctorEntity>(DoctorsRepo)

    var name by DoctorsRepo.name
    var confirmed by DoctorsRepo.confirmed
    var country by DoctorsRepo.country
    var dateUpdated by DoctorsRepo.dateUpdated

    val timeslots by TimeslotEntity referrersOn TimeslotsRepo.doctor

    fun toDoctor() = Doctor(
            id = id.value,
            name = name,
            confirmed = confirmed,
            country = Country.values()[country],
            dateUpdated = Instant.ofEpochMilli(dateUpdated).atZone(ZoneId.systemDefault()).toLocalDateTime()
    )
}

data class Doctor(
        val id: Int,
        val name: String,
        val confirmed: Boolean,
        val country: Country,
        val dateUpdated: LocalDateTime
)

data class NewDoctor(
        val name: String,
        val country: Country
)
