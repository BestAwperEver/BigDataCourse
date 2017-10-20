package com.asuscomm.radagast.bd2017

class MyRunnable(val n: Int, val threadNum: Int) : Runnable {
    override fun run() {
        var res: Int = 1
        for (i in 1..n) {
            res *= i
        }

        println("Thread $threadNum: $res")
    }
}

fun main(args: Array<String>) {
    val thread1 = Thread(MyRunnable(5, 1))
    val thread2 = Thread(MyRunnable(10, 2))

    thread1.start()
    thread2.start()

    thread1.join()
    thread2.join()
}