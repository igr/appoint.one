package web

import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import model.*


// doctor

fun getDoctor(id: Int): Doctor {
	return Given {
		pathParam("id", id)
	} When {
		get("/doctors/{id}")
	} Then {
		statusCode(200)
	} Extract {
		`as`(Doctor::class.java)
	}
}

fun postDoctor(newDoctor: NewDoctor): Doctor {
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

fun postTimeslot(newDoctorTimeslot: NewDoctorTimeslots): List<Timeslot> {
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

// auth

fun userLogin(credentials: EmailPasswordCredential): User {
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

fun user(token: String): User {
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
