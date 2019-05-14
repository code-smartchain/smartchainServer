package com.smartchain.server.entity.config

class AgentConfig (
        val id: String,
        val publicAddress: String,
        val keystoreFile: String
) {
    val name = id
}
