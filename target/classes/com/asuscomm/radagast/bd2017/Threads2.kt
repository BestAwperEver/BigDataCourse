package com.asuscomm.radagast.bd2017

fun main(args: Array<String>) {
    Thread({
        println("Hello from thread #1")
    }).start()

    kotlin.concurrent.thread {
        println("Hello from thread #2")
    }
}