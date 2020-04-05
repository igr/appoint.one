package repo

import model.Doctor
import model.DoctorCertificate
import model.DoctorData
import model.DoctorSex
import ofEpochMilliseconds
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.InsertStatement

object DoctorsTable : IdTable<Int>(name = "doctors") {
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
	val confirmed = bool("confirmed")
	val dateUpdated = long("dateUpdated").clientDefault { System.currentTimeMillis() }

	val userId = integer("user_id").references(UsersTable.id)
}

fun ResultRow.toDoctorData() = DoctorData(
	name = this[DoctorsTable.name],
	email = this[DoctorsTable.email],
	sex = DoctorSex.of(this[DoctorsTable.sex]),
//		country = Country.of(country),
	year = this[DoctorsTable.year],
	education = this[DoctorsTable.education],
	occupation = this[DoctorsTable.occupation],
	occupation2 = this[DoctorsTable.occupation2],
	occupationSpec = this[DoctorsTable.occupationSpec],
	certificate = DoctorCertificate.of(this[DoctorsTable.certificate]),
	modalitet = this[DoctorsTable.modalitet],
	modalitet2 = this[DoctorsTable.modalitet2],
	phone = this[DoctorsTable.phone],
	zoom = this[DoctorsTable.zoom],
	confirmed = this[DoctorsTable.confirmed],
	dateUpdated = ofEpochMilliseconds(this[DoctorsTable.dateUpdated])
)

fun ResultRow.toDoctor() = Doctor(
	id = this[DoctorsTable.id].value,
	data = toDoctorData(),
	userId = this[DoctorsTable.userId]
)

fun DoctorData.data(): DoctorsTable.(InsertStatement<Number>) -> Unit {
	return {
		it[name] = this@data.name
		it[email] = this@data.email
		it[sex] = this@data.sex.value
		it[country] = 1
		it[year] = this@data.year
		it[education] = this@data.education
		it[occupation] = this@data.occupation
		it[occupation2] = this@data.occupation2
		it[occupationSpec] = this@data.occupationSpec
		it[certificate] = this@data.certificate.value
		it[modalitet] = this@data.modalitet
		it[modalitet2] = this@data.modalitet2
		it[phone] = this@data.phone
		it[zoom] = this@data.zoom
		it[confirmed] = false
		it[dateUpdated] = System.currentTimeMillis()
	}
}
