package com.smartchain.server.service

import info.laht.yajrpc.RpcParams
import info.laht.yajrpc.net.ws.RpcWebSocketClient
import org.springframework.context.annotation.Profile

@Profile("production")
class RpcWs (
        url: String,
        port: Int
) : RpcWsInterface {

    companion object Factory {
        fun create(url: String, port: Int): RpcWs {
            return RpcWs(url, port)
        }
    }

    private val con = RpcWebSocketClient(url, port)

    override fun <T> call(methodName: String, params: RpcParams?, resultClass: Class<T>): T? {
        val notNullParams = params ?: RpcParams.listParams()
        return con.write(methodName, notNullParams, 50000).get().getResult(resultClass)
    }

    override fun closeConnection() {
        con.close()
    }
}
