package com.smartchain.server.service

import com.smartchain.server.entity.User
import com.smartchain.server.entity.config.AgentConfig
import com.smartchain.server.entity.config.InstanceConfig
import com.smartchain.server.entity.config.InterfaceConfig
import com.smartchain.server.entity.config.WebSocketConfig
import com.smartchain.server.entity.input.Access
import com.smartchain.server.entity.input.SendAccessInfo
import info.laht.yajrpc.RpcParams
import java.util.*

class UserService {
    private val rootInstance = "smartchain"
    private val rootWs = WebSocketConfig("localhost", 8887)
    private val websocket = RpcWs(rootWs.url, rootWs.port)
    private val adminConductor = AdminConductor(websocket)

    private val dnaId = "smartchain_dna"

    private val registeredUser = mutableMapOf<String, User>()

    fun registerUser (): String {
        val agentConfig = AgentConfig(
                id = UUID.randomUUID().toString(),
                publicAddress = "",
                keystoreFile = ""
        )

        val instanceConfig = InstanceConfig(
                id = "I_${agentConfig.id}",
                dnaId = dnaId,
                agentId = agentConfig.id
        )

        val interfaceConfig = InterfaceConfig(
                id = "F_${agentConfig.id}",
                port = 8887
        )

        val instanceId =  adminConductor.startNewAgent(agentConfig, instanceConfig, interfaceConfig)
        registeredUser[agentConfig.id] = User(agentConfig, instanceConfig, interfaceConfig)
        return instanceId
    }

    fun allInstanceInfo (): MutableList<InstanceConfig>? {
        return websocket.call("info/instance", null, mutableListOf<InstanceConfig>().javaClass)
    }

    fun createAccess (access: Access, userInstance: String = rootInstance): String? {
        val params = RpcParams.mapParams(mapOf("access" to access))

        return websocket.call("$userInstance/accesses/create_access", params, String::class.java)
    }

    fun sendAccess (sendAccessInfo: SendAccessInfo, userInstance: String = rootInstance): String? {
        val params = RpcParams.mapParams(
                mapOf(
                        "access_addr" to sendAccessInfo.accessAddr,
                        "recipient_addr" to sendAccessInfo.recipientAddr
                )
        )

        return websocket.call("$userInstance/accesses/send_access", params, String::class.java)
    }

    fun getMyAccesses (userInstance: String = rootInstance): String? {
        return websocket.call("$userInstance/accesses/get_my_accesses", null, String::class.java)
    }
}
