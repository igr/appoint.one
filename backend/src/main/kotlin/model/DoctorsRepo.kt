package model

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import java.time.Instant
import java.time.ZoneId

object DoctorsRepo : IdTable<Int>(name = "doctors") {
	override val id: Column<EntityID<Int>> = integer("id").entityId()
	override val primaryKey by lazy { super.primaryKey ?: PrimaryKey(id) }

	val name = varchar("name", 255)
	val email = varchar("email", 255)
	val sex = bool("sex")
	val country = integer("country")

	//	val city = integer("city")
	val year = integer("year")
	val education = integer("education")
	val occupation = integer("occupation")
	val occupation2 = varchar("occupation2", 255)
	val occupationSpec = varchar("occupation_spec", 255)
	val certificate = integer("certificate")
	val modalitet = integer("modalitet")
	val modalitet2 = varchar("modalitet2", 255)

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
	var education by DoctorsRepo.education
	var occupation by DoctorsRepo.occupation
	var occupation2 by DoctorsRepo.occupation2
	var occupationSpec by DoctorsRepo.occupationSpec
	var certificate by DoctorsRepo.certificate
	var modalitet by DoctorsRepo.modalitet
	var modalitet2 by DoctorsRepo.modalitet2

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
//		country = Country.of(country),
		year = year,
		education = education,
		occupation = occupation,
		occupation2 = occupation2,
		occupationSpec = occupationSpec,
		certificate = DoctorCertificate.of(certificate),
		modalitet = modalitet,
		modalitet2 = modalitet2,
		phone = phone,
		zoom = zoom,
		pic = pic,
		confirmed = confirmed,
		dateUpdated = Instant.ofEpochMilli(dateUpdated).atZone(ZoneId.systemDefault()).toLocalDateTime()
	)

	fun toDoctor() = Doctor(
		id = id.value,
		data = toDoctorData(),
		user = userRef.toUser()
	)
}

fun DoctorEntity.Companion.findExisting(userId: Int): DoctorEntity {
	return DoctorEntity.findById(userId)!!
}

fun DoctorEntity.Companion.add(doctor: DoctorData, userEntity: UserEntity): DoctorEntity {
	return DoctorEntity.new(id = userEntity.id.value) {
		name = doctor.name
		email = doctor.email
		sex = doctor.sex.value
		country = 1
		year = doctor.year
		education = doctor.education
		occupation = doctor.occupation
		occupation2 = doctor.occupation2
		occupationSpec = doctor.occupationSpec
		certificate = doctor.certificate.value
		modalitet = doctor.modalitet
		modalitet2 = doctor.modalitet2
		phone = doctor.phone
		zoom = doctor.zoom
		pic = doctor.pic
		confirmed = false
		dateUpdated = System.currentTimeMillis()
		userRef = userEntity
	}
}
