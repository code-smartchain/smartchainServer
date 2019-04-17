package com.smartchain.server.controller

import com.smartchain.server.service.RpcWs
import info.laht.yajrpc.RpcParams
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/client", produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
class UserController {

    @GetMapping("/new")
    fun registerNewClient (): ResponseEntity<*> {

        val response = RpcWs("localhost", 8887).call("info/instances", RpcParams.listParams(""), emptyList<InstanceInfo>().javaClass)

        return ResponseEntity.ok().body(response?.get(0))
    }
}

class InstanceInfo (
    var id: String,
    var dna: String,
    var agent: String
)
