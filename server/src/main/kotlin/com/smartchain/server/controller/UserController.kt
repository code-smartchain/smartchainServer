package com.smartchain.server.controller

import com.smartchain.server.entity.input.Access
import com.smartchain.server.entity.input.SendAccessInfo
import com.smartchain.server.service.UserService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
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

    @PostMapping("/call/create_access/{agentAnimal}")
    fun createAccess (@PathVariable agentAnimal: String, @RequestBody access: Access): ResponseEntity<*> {
        val response = userService.createAccess(access, agentAnimal)
        println(response)
        return ResponseEntity.ok().body(response)
    }

    @PostMapping("call/send_access/{agentAnimal}")
    fun sendAccess (@PathVariable agentAnimal: String, @RequestBody sendAccessInfo: SendAccessInfo): ResponseEntity<*> {
        val response = userService.sendAccess(sendAccessInfo, agentAnimal)

        return ResponseEntity.ok().body(response)
    }

    @GetMapping("call/get_accesses/{agentAnimal}")
    fun getMyAccesses (@PathVariable agentAnimal: String): ResponseEntity<*> {
        val response = userService.getMyAccesses(agentAnimal)

        return ResponseEntity.ok().body(response)
    }
}
