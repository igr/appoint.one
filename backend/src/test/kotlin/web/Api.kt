package web

import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.ValidatableResponse
import model.EmailPasswordCredential
import model.NewDoctor
import model.NewDoctorTimeslots


// doctor

fun getDoctor(id: Int): ValidatableResponse {
	return Given {
		pathParam("id", id)
	} When {
		get("/doctors/{id}")
	} Then {
		statusCode(200)
	}
}

fun postDoctor(newDoctor: NewDoctor): ValidatableResponse {
	return Given {
		body(newDoctor)
		contentType(ContentType.JSON)
	} When {
		post("/doctors")
	} Then {
		statusCode(201)
	}
}


// timeslot

fun postTimeslot(newDoctorTimeslot: NewDoctorTimeslots): ValidatableResponse {
	return Given {
		body(newDoctorTimeslot)
		contentType(ContentType.JSON)
	} When {
		post("/timeslots")
	} Then {
		statusCode(201)
	}
}

// auth

fun login(credentials: EmailPasswordCredential): ValidatableResponse {
	return Given {
		body(credentials)
		contentType(ContentType.JSON)
	} When {
		post("/users/login")
	} Then {
		statusCode(200)
	}
}