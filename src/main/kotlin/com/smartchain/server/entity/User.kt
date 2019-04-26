package com.smartchain.server.entity

import com.smartchain.server.entity.config.AgentConfig
import com.smartchain.server.entity.config.InstanceConfig
import com.smartchain.server.entity.config.InterfaceConfig

class User (
        val animal: String,
        val publicAddress: String,
        val port: Int,
        val agentAddress: String
)
