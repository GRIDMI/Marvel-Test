package com.gridmi.marvel

object Constant {

    const val URL = "https://gateway.marvel.com:443/v1/public/"

    const val PUBLIC_KEY = "a742217b7f1261042e9f840f350ed474"
    const val PRIVATE_KEY = "2289de5298299c28dd06abd653f2549d7da69cd0"

    fun getHandlerPoint(chunk:String) : String {
        return "$URL/$chunk"
    }

}