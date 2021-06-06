package com.kurtlike.lab5android.datainput

import org.json.JSONObject

class Dot() {
    constructor(_x: Double, _y:Double) : this() {
       x = _x
       y = _y
    }
    var x = 0.0
    var y = 0.0

    constructor(jsonStr: String) : this() {
        val jsonObject = JSONObject(jsonStr)
        x = jsonObject.getDouble("x")
        y = jsonObject.getDouble("y")
    }
    fun toJsonString(): String{
        val str = "{\"x\":\"$x\",\"y\":\"$y\"}"
        return str
    }

}