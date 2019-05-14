package com.smartchain.server.service

import info.laht.yajrpc.RpcParams

interface RpcWsInterface {
    fun <T> call(methodName: String, params: RpcParams?, resultClass: Class<T>): T?

    fun closeConnection()
}