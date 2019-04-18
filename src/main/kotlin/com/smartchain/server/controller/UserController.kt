package com.smartchain.server.controller

import com.smartchain.server.entity.*
import com.smartchain.server.service.RpcWs
import info.laht.yajrpc.RpcParams
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/client", produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
class UserController {

    val rootWs = WebSocketInfo("localhost", 8887)
    val websocket = RpcWs(rootWs.url, rootWs.port)
    val rootInstance = "smartchain"

    @GetMapping("/new")
    fun registerNewClient (): ResponseEntity<*> {

        val response = websocket.call("info/instances", RpcParams.noParams(), mutableListOf<InstanceInfo>().javaClass)

        return ResponseEntity.ok().body(response)
    }

    @GetMapping("/info")
    fun allInstanceInfo (): ResponseEntity<*> {
        val response = websocket.call("info/instances", RpcParams.noParams(), mutableListOf<InstanceInfo>().javaClass)

        return ResponseEntity.ok().body(response)
    }

    @PostMapping("/call/create_access")
    fun createAccess (@RequestBody access: Access): ResponseEntity<*> {
        val params = RpcParams.mapParams(mapOf("access" to access))

        val response = websocket.call("$rootInstance/accesses/create_access", params, String::class.java)

        return ResponseEntity.ok().body(response)
    }

    @PostMapping("call/send_access")
    fun sendAccess (@RequestBody sendAccessInfo: SendAccessInfo): ResponseEntity<*> {
        val params = RpcParams.mapParams(mapOf("access_addr" to sendAccessInfo.accessAddr, "recipient_addr" to sendAccessInfo.recipientAddr))

        val response = websocket.call("$rootInstance/accesses/send_access", params, String::class.java)

        return ResponseEntity.ok().body(response)
    }

    @PostMapping("call/get_accesses")
    fun getMyAccesses (): ResponseEntity<*> {
        val response = websocket.call("$rootInstance/accesses/get_my_accesses", null, String::class.java)

        return ResponseEntity.ok().body(response)
    }
}
