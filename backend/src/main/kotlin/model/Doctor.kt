package model

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object DoctorsRepo : IntIdTable(name = "doctors") {
	val name = varchar("name", 255)
	val sex = bool("sex")
	val country = integer("country")

	//	val city = integer("city")
	val year = integer("year")
	val occupation = varchar("occupation", 255)
	val education = integer("education")
	val phone = varchar("phone", 32)
	val zoom = varchar("zoom", 32)
	val pic = bool("pic")
	val confirmed = bool("confirmed")
	val dateUpdated = long("dateUpdated").clientDefault { System.currentTimeMillis() }
}

class DoctorEntity(id: EntityID<Int>) : Entity<Int>(id) {
	companion object : EntityClass<Int, DoctorEntity>(DoctorsRepo)

	var name by DoctorsRepo.name
	var sex by DoctorsRepo.sex
	var country by DoctorsRepo.country

	//	var city by DoctorsRepo.city
	var year by DoctorsRepo.year
	var occupation by DoctorsRepo.occupation
	var education by DoctorsRepo.education
	var phone by DoctorsRepo.phone
	var zoom by DoctorsRepo.zoom
	var pic by DoctorsRepo.pic
	var confirmed by DoctorsRepo.confirmed
	var dateUpdated by DoctorsRepo.dateUpdated

	val timeslots by TimeslotEntity referrersOn TimeslotsRepo.doctor

	fun toDoctor() = Doctor(
		id = DoctorId(id.value),
		name = name,
		sex = DoctorSex.of(sex),
		country = Country.of(country),
		year = year,
		occupation = occupation,
		education = education,
		phone = phone,
		zoom = zoom,
		pic = pic,
		confirmed = confirmed,
		dateUpdated = Instant.ofEpochMilli(dateUpdated).atZone(ZoneId.systemDefault()).toLocalDateTime()
	)
}

fun DoctorEntity.Companion.findExisting(id: Int): DoctorEntity {
	return find { DoctorsRepo.id eq id }.single()
}

fun DoctorEntity.Companion.add(doctor: NewDoctor): DoctorEntity {
	return DoctorEntity.new {
		name = doctor.name
		sex = doctor.sex.value
		country = doctor.country.value
		year = doctor.year
		occupation = doctor.occupation
		education = doctor.education
		phone = doctor.phone
		zoom = doctor.zoom
		pic = doctor.pic
		confirmed = false
		dateUpdated = System.currentTimeMillis()
	}
}

/* DOMAIN */

data class DoctorId(
	val value: Int
)

enum class DoctorSex(val value: Boolean) {
	MALE(true), FEMALE(false);

	companion object {
		fun of(value: Boolean): DoctorSex = if (value) MALE else FEMALE;
	}
}

data class Doctor(
	val id: DoctorId,
	val name: String,
	val sex: DoctorSex,
	val country: Country,
//	val city: City,
	val year: Int,
	val occupation: String,
	val education: Int,
	val phone: String,
	val zoom: String,
	val pic: Boolean,
	val confirmed: Boolean,
	val dateUpdated: LocalDateTime
)

data class NewDoctor(
	val name: String,
	val sex: DoctorSex,
	val country: Country,
//	val city: City,
	val year: Int,
	val occupation: String,
	val education: Int,
	val phone: String,
	val zoom: String,
	val pic: Boolean = false
)
