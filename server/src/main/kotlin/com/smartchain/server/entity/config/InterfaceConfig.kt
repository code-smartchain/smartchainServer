package com.smartchain.server.entity.config

class InterfaceConfig (
        val id: String,
        val port: Int,
        val type: String = "websocket",
        val admin: Boolean = false
)
