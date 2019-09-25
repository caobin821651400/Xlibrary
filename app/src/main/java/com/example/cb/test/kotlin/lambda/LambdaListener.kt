package com.example.cb.test.kotlin.lambda

class LambdaListener {
    //1. -->代表无参数的回调
    private var listener1: (() -> Unit)? = null

    fun setListener(listener: (() -> Unit)) {
        this.listener1 = listener
    }

    fun onClick() {
        listener1?.invoke()
    }


    //2. -->代表有参数无返回值的回调
    private var listener2: ((position: Int) -> Unit)? = null

    fun setListener2(listener: ((position: Int) -> Unit)) {
        this.listener2 = listener
    }

    fun onClick2() {
        listener2?.invoke(100)
    }


    //3. -->代表有参数有返回值的回调
    private var listener3: ((position: Int) -> Int)? = null

    fun setListener3(listener: ((position: Int) -> Int)) {
        this.listener3 = listener
    }

    fun onClick3() {
        var value = listener3?.invoke(200)
        System.err.println("返回值是$value")
    }
}