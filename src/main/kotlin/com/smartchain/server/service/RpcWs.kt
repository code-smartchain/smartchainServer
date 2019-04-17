package com.smartchain.server.service

import info.laht.yajrpc.RpcParams
import info.laht.yajrpc.net.ws.RpcWebSocketClient

class RpcWs (
        val url: String,
        val port: Int
) {

    val con: RpcWebSocketClient

    init {
        con = RpcWebSocketClient(url, port)
    }

    public fun <T> call(methodName: String, params: RpcParams, resultClass: Class<T>): T? {
        return con.write(methodName, params).get().getResult(resultClass)
    }

    public fun closeConnection() {
        con.close()
    }
}
