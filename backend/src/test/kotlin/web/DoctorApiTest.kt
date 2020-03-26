package web

import common.ServerTest
import io.restassured.RestAssured.get
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import model.Doctor
import model.NewDoctor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DoctorApiTest : ServerTest() {

    @Test
    fun `POST doctor`() {
        // when
        val newDoctor = NewDoctor(null, "doc11")
        val created = addDoctor(newDoctor)
        
        val retrieved = get("/doctor/{id}", created.id)
            .then()
            .extract().to<Doctor>()

        // then
        assertThat(created.name).isEqualTo(newDoctor.name)
        assertThat(created).isEqualTo(retrieved)
    }

    private fun addDoctor(newDoctor: NewDoctor): Doctor {
        return given()
            .contentType(ContentType.JSON)
            .body(newDoctor)
            .When()
            .post("/doctor")
            .then()
            .statusCode(201)
            .extract().to()
    }

}