package com.smartchain.server.service

import com.smartchain.server.entity.User
import com.smartchain.server.entity.config.InstanceConfig
import com.smartchain.server.entity.config.WebSocketConfig
import com.smartchain.server.entity.input.Access
import com.smartchain.server.entity.input.SendAccessInfo
import info.laht.yajrpc.RpcParams
import java.io.BufferedReader
import java.io.File
import java.lang.Thread.sleep
import java.util.*
import java.util.concurrent.TimeUnit

class UserService {
    private val rootInstance = "smartchain"
    private val rootWs = WebSocketConfig("localhost", 8887)
    private val websocket = RpcWs(rootWs.url, rootWs.port)
    private val adminConductor = AdminConductor(websocket)

    private val dnaId = "smartchain_dna"

    private val publicAddr = listOf("HcSCIaRpUfTw6jqrun3d4QEsg7TIu3e7ufKDmR786b8ajkg4u6nvBpfYesuywoz","HcSCIdoUOoD6apne5n3IZ9mgySj4G366x758k87km37cw54j4zs3O4BpytWu3hz","HcScIatqQHkuv39azjxM5KExROzrvvjy53A3K5ikvoAeud5x6ozp478RqPpmhpr","HcScjoDi883QqQ3qk6RS9aPMOSExzupi6n9GyobY8S4ThrqubjtC47QTzIqq5ri","HcScJzmQACi64q85558sPuhMnf6Hmj4xm3xRSvpw754x99Y4rp7mTQEc3U9odvi")
    private val keysAddr = "/Users/jonny/Desktop/SmartChain/SmartChain/smartchainServer/keys/"

    private var bootstrapNode = ""
    private val registeredUser = mutableMapOf<String, User>()
    private var animals = File("animals.txt").bufferedReader().readLines().toMutableList()

    /*fun registerUser (): String {
        val newIndex = registeredUser.size

        val agentConfig = AgentConfig(
                id = UUID.randomUUID().toString(),
                publicAddress = publicAddr[newIndex],
                keystoreFile = keysAddr + publicAddr[newIndex]
        )

        val instanceConfig = InstanceConfig(
                id = "I${agentConfig.id}",
                dnaId = dnaId,
                agentId = agentConfig.id
        )

        val interfaceConfig = InterfaceConfig(
                id = "F${agentConfig.id}",
                port = (8889 + newIndex)
        )

        adminConductor.startNewAgent(agentConfig, instanceConfig, interfaceConfig)
        val agentAddr = getAgentAddr(interfaceConfig, instanceConfig)

        registeredUser[agentConfig.id] = User(agentConfig, instanceConfig, interfaceConfig, agentAddr)
        return instanceConfig.id
    }*/

    fun registerUser2 (): String {
        val newIndex = registeredUser.size

        val port = (8889 + newIndex)
        val publicAddress = publicAddr[newIndex]
        val output = "Nice$newIndex.log"
        val n3hPath = "/tmp"

        "sh startInstance.sh $publicAddress $port $bootstrapNode $output $n3hPath".runCommand()

        if (bootstrapNode == "") {
            bootstrapNode = findBootstrapUrl("../smartchainhApp/hApp/$output")
        }
        val agentAddr = findAgentAddr("../smartchainhApp/hApp/$output")
        val agentAnimal = randomAnimal()

        registeredUser[agentAnimal] = User(agentAnimal, publicAddress, port, agentAddr)

        return agentAnimal
    }

    fun allInstanceInfo (): MutableList<InstanceConfig>? {
        return websocket.call("info/instances", null, mutableListOf<InstanceConfig>().javaClass)
    }

    fun createAccess (access: Access, agentAnimal: String): String? {
        val client = registeredUser.filter { it.value.animal == agentAnimal }[agentAnimal] ?: return "ERROR"
        val clientWs = RpcWs("localhost", client.port)

        val params = RpcParams.mapParams(mapOf("access" to access))

        val response =  clientWs.call("smartchain/accesses/create_access", params, String::class.java)
        clientWs.closeConnection()

        return response
    }

    fun sendAccess (sendAccessInfo: SendAccessInfo, agentAnimal: String): String? {
        val client = registeredUser.filter { it.value.animal == agentAnimal }[agentAnimal] ?: return "ERROR"
        val clientWs = RpcWs("localhost", client.port)

        val recipientUser = registeredUser.filter { it.value.animal == sendAccessInfo.recipientAnimal }[sendAccessInfo.recipientAnimal] ?: return "ERROR"

        val params = RpcParams.mapParams(
                mapOf(
                        "access_addr" to sendAccessInfo.accessAddr,
                        "recipient_addr" to recipientUser.publicAddress
                )
        )

        val response = clientWs.call("smartchain/accesses/send_access", params, String::class.java)
        clientWs.closeConnection()

        return response
    }

    fun getMyAccesses (agentAnimal: String): String? {
        val client = registeredUser.filter { it.value.animal == agentAnimal }[agentAnimal] ?: return "ERROR"
        val clientWs = RpcWs("localhost", client.port)

        val response = clientWs.call("smartchain/accesses/get_my_accesses", null, String::class.java)
        clientWs.closeConnection()

        return response
    }

    /*private fun getAgentAddr(interfaceConfig: InterfaceConfig, instanceConfig: InstanceConfig  ): String {
        val clientWs = RpcWs("localhost", interfaceConfig.port)

        val jsonResponse = clientWs.call("${instanceConfig.id}/accesses/get_agent_address", null, String::class.java)
        return YAJRPC.fromJson(jsonResponse!!, Ok::class.java).Ok
    }*/

    private fun String.runCommand(workingDir: File = File(".")) {
        println(workingDir.absolutePath)
        ProcessBuilder(*split(" ").toTypedArray())
                .directory(workingDir)
                .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .start()
                .waitFor(60, TimeUnit.MINUTES)
    }

    private fun findAgentAddr(filePathName: String): String {
        val regex = Regex("[ \\t]*peerId:[ \\t]*([^\\n\\r]*)")
        return findRegex(regex, filePathName)
    }

    private fun findBootstrapUrl(filePathName: String): String {
        val regex = Regex("[ \\t]*p2p:[ \\t]*\\[([^\\n\\r]*)\\]")
        return findRegex(regex, filePathName)
    }

    private fun findRegex(regex: Regex, filePathName: String): String {
        var addr : MatchResult? = null
        var inputString: String?
        var bufferedReader: BufferedReader

        for (i in 0..2) {
            bufferedReader = File(filePathName).bufferedReader()

            inputString = bufferedReader.use { it.readText() }
            addr = regex.find(inputString)
            sleep(1000)
        }

        return addr!!.groups[1]!!.value
    }

    private fun randomAnimal(): String {
        val maxIndex = animals.size - 1
        val randomIndex = (0..maxIndex).random()
        return animals.removeAt(randomIndex)
    }

    private fun IntRange.random() = Random().nextInt((endInclusive + 1) - start) +  start
}
