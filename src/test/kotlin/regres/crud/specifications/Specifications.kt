package regres.crud.specifications

import io.restassured.RestAssured
import io.restassured.http.ContentType

object Specifications {
    fun requestSpecification(baseUrl: String) = RestAssured.given()
        .baseUri(baseUrl)
        .contentType(ContentType.JSON)
        .log().all()

    fun responseSpecificationOK200() = RestAssured.expect()
        .statusCode(200)
        .log().all()
    fun responseSpecification400BAD() = RestAssured.expect()
        .statusCode(400)
        .log().all()
    fun responseSpecification201CREATED() = RestAssured.expect()
        .statusCode(201)
        .log().all()
}