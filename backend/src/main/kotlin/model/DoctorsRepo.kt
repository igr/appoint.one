package model

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import java.time.Instant
import java.time.ZoneId

object DoctorsRepo : IntIdTable(name = "doctors") {
	val name = varchar("name", 255)
	val email = varchar("email", 255)
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

	val userRef = reference("user_id", UsersRepo.id)
}

class DoctorEntity(id: EntityID<Int>) : Entity<Int>(id) {
	companion object : EntityClass<Int, DoctorEntity>(DoctorsRepo)

	var name by DoctorsRepo.name
	var email by DoctorsRepo.email
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

	var userRef by UserEntity referencedOn DoctorsRepo.userRef
	val timeslots by TimeslotEntity referrersOn TimeslotsRepo.doctoreRef

	private fun toDoctorData() = DoctorData(
		name = name,
		email = email,
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

	fun toDoctor() = Doctor(
		id = DoctorId(id.value),
		data = toDoctorData(),
		user = userRef.toUser()
	)
}

fun DoctorEntity.Companion.findById(userId: DoctorId): DoctorEntity? {
	return findById(userId.value)
}

fun DoctorEntity.Companion.findExisting(userId: DoctorId): DoctorEntity {
	return findById(userId.value)!!
}

fun DoctorEntity.Companion.add(doctor: DoctorData, userEntity: UserEntity): DoctorEntity {
	return DoctorEntity.new {
		name = doctor.name
		email = doctor.email
		sex = doctor.sex.value
		country = doctor.country.id
		year = doctor.year
		occupation = doctor.occupation
		education = doctor.education
		phone = doctor.phone
		zoom = doctor.zoom
		pic = doctor.pic
		confirmed = false
		dateUpdated = System.currentTimeMillis()
		userRef = userEntity
	}
}
