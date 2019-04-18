package com.smartchain.server.entity

import com.smartchain.server.entity.config.AgentConfig
import com.smartchain.server.entity.config.InstanceConfig
import com.smartchain.server.entity.config.InterfaceConfig

class User (
        val agentConfig: AgentConfig,
        val instanceConfig: InstanceConfig,
        val interfaceConfig: InterfaceConfig
)
