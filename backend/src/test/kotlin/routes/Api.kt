package routes

import auth.LoginCredential
import domain.appointment.Appointment
import domain.doctor.Doctor
import domain.timeslot.NewTimeslot
import domain.timeslot.Timeslot
import domain.user.NewDoctorUser
import domain.user.User
import id.DoctorId
import id.TimeslotId
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.builtins.list
import server.json

val httpClient = HttpClient(CIO) {
	install(JsonFeature) {
		serializer = KotlinxSerializer()
	}
}
val baseUrl = "http://localhost:8080"


fun getAppointment(timeslotId: TimeslotId) = runBlocking {
	val response = httpClient.get<HttpResponse>("${baseUrl}/appointments/${timeslotId}")
	Pair(response, json.parse(Appointment.serializer(), response.readText()))
}


fun getDoctor(id: DoctorId) = runBlocking {
	val response = httpClient.get<HttpResponse>("${baseUrl}/doctors/${id}")
	Pair(response, json.parse(Doctor.serializer(), response.readText()))
}

fun postDoctor(newDoctor: NewDoctorUser) = runBlocking {
	val response = httpClient.post<HttpResponse> {
		url("${baseUrl}/doctors")
		contentType(ContentType.Application.Json)
		body = newDoctor
	}

	Pair(response, json.parse(Doctor.serializer(), response.readText()))
}

fun postTimeslot(token: String, newDoctorTimeslot: NewTimeslot) = runBlocking {
	val response = httpClient.post<HttpResponse> {
		url("${baseUrl}/timeslots")
		header("Authorization", "Bearer $token")
		contentType(ContentType.Application.Json)
		body = newDoctorTimeslot
	}

	Pair(response, json.parse(TimeslotId.serializer().list, response.readText()))
}

fun getTimeslot(timeslotId: TimeslotId) = runBlocking {
	val response = httpClient.get<HttpResponse>("${baseUrl}/timeslots/${timeslotId}")
	Pair(response, json.parse(Timeslot.serializer(), response.readText()))
}

fun userLogin(credentials: LoginCredential) = runBlocking {
	val response = httpClient.post<HttpResponse> {
		url("${baseUrl}/login")
		contentType(ContentType.Application.Json)
		body = credentials
	}
	Pair(response, json.parse(User.serializer(), response.readText()))
}

fun userLoginResponse(credentials: LoginCredential) = runBlocking {
	httpClient.post<HttpResponse> {
		url("${baseUrl}/login")
		contentType(ContentType.Application.Json)
		body = credentials
	}
}


fun userGet(token: String) = runBlocking {
	val response = httpClient.get<HttpResponse> {
		url("${baseUrl}/user")
		header("Authorization", "Bearer $token")
	}
	Pair(response, json.parse(User.serializer(), response.readText()))
}

fun userGet() = runBlocking {
	val response = httpClient.get<HttpResponse>("${baseUrl}/user")
	Pair(response, Unit)
}
