package com.smartchain.server.service

import info.laht.yajrpc.RpcParams
import org.springframework.context.annotation.Profile

@Profile("test")
class RpcWsMock(
        url: String,
        port: Int
) : RpcWsInterface {
    override fun <T> call(methodName: String, params: RpcParams?, resultClass: Class<T>): T? {
        return "TestString" as T
    }

    companion object Factory {
        fun create(url: String, port: Int): RpcWsMock {
            return RpcWsMock(url, port)
        }
    }

    override fun closeConnection() {
    }
}
