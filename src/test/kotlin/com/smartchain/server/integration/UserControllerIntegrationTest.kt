package com.smartchain.server.integration

import com.google.gson.Gson
import com.smartchain.server.controller.UserController
import com.smartchain.server.entity.User
import com.smartchain.server.entity.config.InstanceConfig
import com.smartchain.server.entity.input.Access
import com.smartchain.server.entity.input.SendAccessInfo
import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.response.ValidatableResponse
import org.apache.http.HttpStatus
import org.hamcrest.CoreMatchers.isA
import org.hamcrest.Matchers.hasSize
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIntegrationTest {

    /*Define TestServer*/
    @Value("\${local.server.port}")
    private val serverPort: Int = 0

    @Before
    fun setUp() {
        RestAssured.port = serverPort
    }

    // setup environment
    private var gson = Gson()
    final val service: UserController = UserController()
    val registeredUser = service.userService.registeredUser


    @Test
    fun shouldCreateClient() {
        /*Define Test Parameters*/
        val testBody = ""
        val testRoute = "/client/new"

        /*Make Request*/
        val response = mockGetRequest(testBody, testRoute)
        response.statusCode(HttpStatus.SC_OK)
                .body(isA(String::class.java))
    }

    @Test
    fun shouldReturnInfo() {
        /*Define Test Parameters*/
        val testBody = ""
        val testRoute = "/client/info"

        /*Make Request*/
        val response = mockGetRequest(testBody, testRoute)
        response.statusCode(HttpStatus.SC_OK)
                .body("$", hasSize<InstanceConfig>(1))
    }

    @Test
    fun shouldCreateAccess() {
        /*Define Test Parameters*/
        val testAccess = Access("someID", "someType", "someName",
                "someKey", "someDescription", "someRestriction")
        val testUser = User("Racoon", "publicAddress", 23, "someAddr")
        registeredUser[testUser.animal] = testUser
        val testBody = gson.toJson(testAccess)
        val testRoute = "/client/call/create_access/${testUser.animal}"

        /*Make Request*/
        val response = mockPostRequest(testBody, testRoute)
        response.statusCode(HttpStatus.SC_OK)
                .body(isA(String::class.java))
    }

    @Test
    fun shouldShareAccess() {
        /*Define Test Parameters*/
        val testUser = User("Racoon", "publicAddress", 23, "someAddr")
        val testReceipient = User("Giraffe", "publicAddress2", 22, "someAddr2")
        registeredUser[testReceipient.animal] = testReceipient
        registeredUser[testUser.animal] = testUser
        val testSharing = SendAccessInfo("someAddr2", "Giraffe")
        val testBody = gson.toJson(testSharing)
        val testRoute = "/client/call/send_access/${testUser.animal}"

        /*Make Request*/
        val response = mockPostRequest(testBody, testRoute)
        response.statusCode(HttpStatus.SC_OK)
                .body(isA(String::class.java))
    }

    @Test
    fun shouldHaveAccess() {
        /*Define Test Parameters*/
        val testUser = User("Racoon", "publicAddress", 23, "someAddr")
        registeredUser[testUser.animal] = testUser
        val testBody = ""
        val testRoute = "/client/call/get_accesses/${testUser.animal}"

        /*Make Request*/
        val response = mockGetRequest(testBody, testRoute)
        response.statusCode(HttpStatus.SC_OK)
                .body(isA(String::class.java))
    }


    /*Helper Methods*/
    private fun mockGetRequest(content: String, route: String): ValidatableResponse {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(content)
                .get(route)
                .then()
                .log().ifValidationFails()
    }

    private fun mockPostRequest(content: String, route: String): ValidatableResponse {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(content)
                .post(route)
                .then()
                .log().ifValidationFails()
    }
}
