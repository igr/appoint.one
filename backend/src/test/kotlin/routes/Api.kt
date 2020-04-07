package routes

import domain.doctor.Doctor
import domain.doctor.DoctorId
import domain.timeslot.NewTimeslot
import domain.timeslot.Timeslot
import domain.user.NewDoctorUser
import domain.user.User
import io.ktor.auth.UserPasswordCredential
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When


// doctor

fun getDoctor(id: DoctorId): Doctor {
	return Given {
		pathParam("id", id.value)
	} When {
		get("/doctors/{id}")
	} Then {
		statusCode(200)
	} Extract {
		`as`(Doctor::class.java)
	}
}

fun postDoctor(newDoctor: NewDoctorUser): Doctor {
	return Given {
		body(newDoctor)
		contentType(ContentType.JSON)
	} When {
		post("/doctors")
	} Then {
		statusCode(201)
	} Extract {
		`as`(Doctor::class.java)
	}
}


// timeslot

fun postTimeslot(newDoctorTimeslot: NewTimeslot): List<Timeslot> {
	return Given {
		body(newDoctorTimeslot)
		contentType(ContentType.JSON)
	} When {
		post("/timeslots")
	} Then {
		statusCode(201)
	} Extract {
		`as`(Timeslot::class.java.genericSuperclass)
	}
}

fun getTimeslot(timeslotId: Int): Timeslot {
	return Given {
		contentType(ContentType.JSON)
	} When {
		get("/timeslots/${timeslotId}")
	} Then {
		statusCode(200)
	} Extract {
		`as`(Timeslot::class.java)
	}
}


// auth

fun userLogin(credentials: UserPasswordCredential): User {
	return Given {
		body(credentials)
		contentType(ContentType.JSON)
	} When {
		post("/users/login")
	} Then {
		statusCode(200)
	} Extract {
		`as`(User::class.java)
	}
}

fun userGet(token: String): User {
	return Given {
		header("Authorization", "Bearer $token")
		contentType(ContentType.JSON)
	} When {
		get("/user")
	} Then {
		statusCode(200)
	} Extract {
		`as`(User::class.java)
	}
}