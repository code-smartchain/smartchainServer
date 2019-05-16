package com.smartchain.server.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
class VersionController {

    @Value("\${server.version}")
    private val version: String? = null

    @RequestMapping("/version", produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun registerNewClient(): ResponseEntity<*> {
        val response = version
        println("Version $response")
        return ResponseEntity.ok().body(response)
    }
}
