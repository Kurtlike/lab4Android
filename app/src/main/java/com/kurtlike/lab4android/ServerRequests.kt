package com.kurtlike.lab4android

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.kurtlike.lab4android.datainput.Dot
import org.json.JSONArray
import org.json.JSONObject


class ServerRequests(context: Context) {
    val context = context
    val resultDots = ArrayList<Dot>()
    fun getFunctionalDots(url: String, dots: ArrayList<Dot>): ArrayList<Dot>{
        var jsonArray = JSONArray()
        var jsonString = "{\"dots\":["
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

        }, {
            println(it.message)
        })
        requstQueue.add(jsonObjectRequest)
        return resultDots
    }
    fun getXvalue(url: String, xValue: Double): Double{
        val jsonString = "{\"xValue\":\"$xValue\"}"
        val jsonObject = JSONObject(jsonString)
        var xAnswer = 0.0
        val requstQueue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST ,url ,jsonObject, {
            var a = it.get("xValue")
            println(a)

        }, {
            println(it.message)
        })
        requstQueue.add(jsonObjectRequest)
        return xAnswer
    }

}