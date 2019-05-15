package com.smartchain.server.unit

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.smartchain.server.entity.User
import com.smartchain.server.entity.input.Access
import com.smartchain.server.entity.input.SendAccessInfo
import com.smartchain.server.service.RpcWsInterface
import com.smartchain.server.service.UserService
import com.smartchain.server.utils.Utils
import info.laht.yajrpc.RpcParams
import junit.framework.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.io.File


@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner::class)
@SpringBootTest
class UserServiceTests {

    var testUserService = UserService()
    val utils = Utils()
    val allAnimals = utils.getAnimals()

    @Test
    fun registerUser_Result_Test() {
        val newAgentName = testUserService.registerUser()
        assertNotNull("Agent Name should not be null", newAgentName)
    }

    @Test
    fun registerUser_Correct_Test() {
        val newAgentName = testUserService.registerUser()
        assertTrue("Agent name should be animal from file", allAnimals.contains(newAgentName))
    }

    @Test
    fun createAccess_Test() {
        testUserService.registeredUser.put("Bonobo", User("Bonobo", "", 12, ""))

        val rpcMock: RpcWsInterface = mock()

        val testAccess = Access("", "", "", "", "", "")
        val params = RpcParams.mapParams(mapOf("access" to testAccess))

        val testString = "TestString"

        given(rpcMock.call("smartchain/accesses/create_access", params, String::class.java)).willReturn(testString)

        val accessAddr = testUserService.createAccess(testAccess, "Bonobo", rpcMock, params)

        assertEquals(testString, accessAddr)
        verify(rpcMock).call("smartchain/accesses/create_access", params, String::class.java)
        verify(rpcMock).closeConnection()
    }

    @Test
    fun getMyAccesses_Test() {
        testUserService.registeredUser.put("Bonobo", User("Bonobo", "", 12, ""))
        val rpcMock: RpcWsInterface = mock()
        val testString = "TestString"

        given(rpcMock.call("smartchain/accesses/get_my_accesses", null, String::class.java)).willReturn(testString)

        val accesses = testUserService.getMyAccesses("Bonobo", rpcMock)

        assertEquals(testString, accesses)
        verify(rpcMock).call("smartchain/accesses/get_my_accesses", null, String::class.java)
        verify(rpcMock).closeConnection()

    }

    @Test
    fun sendAccesses_Test() {
        testUserService.registeredUser["Bonobo"] = User("Bonobo", "", 12, "")
        testUserService.registeredUser["Lion"] = User("Lion", "1234", 13, "4321")

        val rpcMock: RpcWsInterface = mock()

        val testAccess = Access("", "", "", "", "", "")
        val params = RpcParams.mapParams(
                mapOf(
                        "access_addr" to "1234",
                        "recipient_addr" to "4321"
                )
        )

        val testString = "TestString"

        given(rpcMock.call("smartchain/accesses/send_access", params, String::class.java)).willReturn(testString)

        val accessAddr = testUserService.sendAccess(SendAccessInfo("", "Lion"), "Bonobo", rpcMock, params)

        assertEquals(testString, accessAddr)
        verify(rpcMock).call("smartchain/accesses/send_access", params, String::class.java)
        verify(rpcMock).closeConnection()
    }
}