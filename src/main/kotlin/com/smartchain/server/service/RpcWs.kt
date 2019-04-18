package com.smartchain.server.service

import info.laht.yajrpc.RpcParams
import info.laht.yajrpc.net.ws.RpcWebSocketClient

class RpcWs (
        url: String,
        port: Int
) {

    private val con = RpcWebSocketClient(url, port)

    fun <T> call(methodName: String, params: RpcParams?, resultClass: Class<T>): T? {
        val notNullParams = params ?: RpcParams.mapParams()

        return con.write(methodName, notNullParams).get().getResult(resultClass)
    }

    fun closeConnection() {
        con.close()
    }
}
