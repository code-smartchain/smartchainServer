package com.smartchain.server.controller

import com.smartchain.server.entity.input.Access
import com.smartchain.server.entity.input.SendAccessInfo
import com.smartchain.server.service.UserService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/client", produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
class UserController {
    val userService = UserService()

    @GetMapping("/new")
    fun registerNewClient (): ResponseEntity<*> {
        val userInstanceId = userService.registerUser()

        return ResponseEntity.ok().body(userInstanceId)
    }

    @GetMapping("/info")
    fun allInstanceInfo (): ResponseEntity<*> {
        val response = userService.allInstanceInfo()

        return ResponseEntity.ok().body(response)
    }

    @PostMapping("/call/create_access")
    fun createAccess (@RequestBody access: Access): ResponseEntity<*> {
        val response = userService.createAccess(access)

        return ResponseEntity.ok().body(response)
    }

    @PostMapping("call/send_access")
    fun sendAccess (@RequestBody sendAccessInfo: SendAccessInfo): ResponseEntity<*> {
        val response = userService.sendAccess(sendAccessInfo)

        return ResponseEntity.ok().body(response)
    }

    @PostMapping("call/get_accesses")
    fun getMyAccesses (): ResponseEntity<*> {
        val response = userService.getMyAccesses()

        return ResponseEntity.ok().body(response)
    }
}
