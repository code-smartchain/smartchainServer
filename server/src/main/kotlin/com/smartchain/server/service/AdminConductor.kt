package com.smartchain.server.service

import com.smartchain.server.entity.Ok
import com.smartchain.server.entity.Success
import com.smartchain.server.entity.config.AgentConfig
import com.smartchain.server.entity.config.InstanceConfig
import com.smartchain.server.entity.config.InterfaceConfig
import info.laht.yajrpc.RpcParams
import info.laht.yajrpc.YAJRPC

class AdminConductor (
        private val ws: RpcWs
) {

    fun startNewAgent(agentConfig: AgentConfig, instanceConfig: InstanceConfig, interfaceConfig: InterfaceConfig) {

        addAgent(agentConfig)
        addInstance(instanceConfig)
        startInstance(instanceConfig.id)
        addInterface(interfaceConfig)
        addInstanceToInterface(instanceConfig.id, interfaceConfig.id)
    }

    private fun addAgent(agentConfig: AgentConfig) {
        val params = RpcParams.mapParams(
                "id" to agentConfig.id,
                "name" to agentConfig.name,
                "public_address" to agentConfig.publicAddress,
                "keystore_file" to agentConfig.keystoreFile
        )

        val response =  ws.call("admin/agent/add", params, Success::class.java)
    }

    private fun addInstance(instanceConfig: InstanceConfig) {
        val params = RpcParams.mapParams(
                "id" to instanceConfig.id,
                "agent_id" to instanceConfig.agentId,
                "dna_id" to instanceConfig.dnaId
        )

        val a = "A)=B&7"

        val response =  ws.call("admin/instance/add", params, Success::class.java)
    }

    private fun startInstance(id: String) {
        val params = RpcParams.mapParams(
                "id" to id
        )

        val response =  ws.call("admin/instance/start", params, Success::class.java)
    }

    private fun addInterface(interfaceConfig: InterfaceConfig) {
        val params = RpcParams.mapParams(
                "id" to interfaceConfig.id,
                "port" to interfaceConfig.port,
                "type" to interfaceConfig.type,
                "admin" to interfaceConfig.admin
        )

        val response =  ws.call("admin/interface/add", params, Success::class.java)
    }

    private fun addInstanceToInterface(instanceId: String, interfaceId: String) {
        val params = RpcParams.mapParams(
                "instance_id" to instanceId,
                "interface_id" to interfaceId
        )

        val response =  ws.call("admin/interface/add_instance", params, Success::class.java)
    }
}
