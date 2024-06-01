package regres.crud

import io.qameta.allure.Description
import io.restassured.RestAssured.given
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import regres.crud.dto.*
import regres.crud.specifications.Specifications

class FirstCrudTest {

    companion object {
        const val URL = "https://reqres.in"
    }

    @Description("This is a test for checking the status code of the API")
    @Test
    fun testCreate() {
        val requestSpec = Specifications.requestSpecification(URL)
        val responseSpec = Specifications.responseSpecificationOK200()

        val users: List<UserData> = given(requestSpec)
            .`when`()
            .get("/api/users?page=2")
            .then()
            .spec(responseSpec)
            .log().all()
            .extract()
            .body()
            .jsonPath()
            .getList("data", UserData::class.java)

        val avatars = users.map { it.avatar }
        val ids = users.map { it.id.toString() }

        for (i in avatars.indices) {
            assertTrue(
                avatars[i].contains(ids[i]),
                "Avatar does not contain ID: ${avatars[i]} does not contain ${ids[i]}"
            )
        }

    }

    @Description("This is a test for checking the status code of the API")
    @Test
    fun testBadRegister() {
        val requestSpec = Specifications.requestSpecification(URL)
        val responseSpec = Specifications.responseSpecification400BAD()

        val user = RegRequest("sydney@fife","")

        val response = given(requestSpec)
            .body(user)
            .`when`()
            .post("/api/register")
            .then()
            .spec(responseSpec)
            .log().all()
            .extract()
            .`as`(BadRegister::class.java)

        response.error?.let { assertTrue(it.contains("Missing password")) }

    }

    @Test
    fun testSuccessfulRegister() {
        val requestSpec = Specifications.requestSpecification(URL)
        val responseSpec = Specifications.responseSpecificationOK200()

        val regRequest = RegRequest("eve.holt@reqres.in","pistol")
        val response = given(requestSpec)
            .body(regRequest)
            .`when`()
            .post("/api/register")
            .then()
            .spec(responseSpec)
            .log().all()
            .extract()
            .`as`(RegisterResponse::class.java)

        assertTrue(response.id==4)
        assertTrue(response.token=="QpwL5tke4Pnpja7X4")
    }

    @Test
    fun testSuccessfulLogin() {
        val requestSpec = Specifications.requestSpecification(URL)
        val responseSpec = Specifications.responseSpecificationOK200()

        val regRequest = RegRequest("eve.holt@reqres.in","cityslicka")
        val actual = given(requestSpec)
            .body(regRequest)
            .`when`()
            .post("/api/login")
            .then()
            .spec(responseSpec)
            .log().all()
            .extract().body().`as`(TokenResponse::class.java)

        assertTrue(actual.token=="QpwL5tke4Pnpja7X4")
    }





}