package com.example.cb.test.jetpack.livedata

import org.json.JSONObject

object LiveDataBusEvent {

    private const val KEY_TWO = "EEE";


    fun getFromData() = LiveDataBus.with<JSONObject>(KEY_TWO)
}