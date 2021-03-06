package com.kurtlike.lab5android

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.kurtlike.lab5android.datainput.Dot
import org.json.JSONArray
import org.json.JSONObject


class ServerRequests(context: Context) {
    val context = context
    val resultDots = ArrayList<Dot>()
    var xAnswer = 0.0

    fun getFunctionalDots(url: String, dots: ArrayList<Dot>,methodName: String, callback: (result: ArrayList<Dot>) ->Unit){
        var jsonArray = JSONArray()
        var jsonString = "{ \"methodName\": \"$methodName\",\"dots\":["
        dots.forEach {
            jsonString += it.toJsonString() + ","
        }
        jsonString = jsonString.substring(0, jsonString.length - 1)
        jsonString += "]}"
        var jsonObject = JSONObject(jsonString)

        val requstQueue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST ,url ,jsonObject, {
            jsonArray = it.getJSONArray("dots")
            resultDots.clear()
            for(i in 0 until jsonArray.length()){
                val dot = Dot(jsonArray[i].toString())
                resultDots.add(dot)
            }
            callback(resultDots)

        }, {
            println(it.message)
        })
        requstQueue.add(jsonObjectRequest)

    }
    fun getXvalue(url: String, xValue: Double, callback: (result: Double) ->Unit){
        val jsonString = "{\"xValue\":$xValue}"
        val jsonObject = JSONObject(jsonString)
        val requstQueue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST ,url ,jsonObject, {
            xAnswer = it.getDouble("xValue")
            callback(xAnswer)

        }, {
            println(it.message)
        })
        requstQueue.add(jsonObjectRequest)
    }

}