package com.kurtlike.lab4android

import android.content.Context
import android.util.Log
import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.kurtlike.lab4android.datainput.Dot
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException


class ServerRequests(context: Context) {
    val context = context

    fun getDunctionalDots(url: String, dots: ArrayList<Dot>){

        try {
            val requestQueue = Volley.newRequestQueue(context)
            val jsonBody = JSONObject()
            var requestBody = "{\"dots\":["
            dots.forEach {
                requestBody+="{\"x\":\"${it.x}\",\"y\":\"${it.y}\"},"
            }
            requestBody =  requestBody.substring(0, requestBody.length - 1)
            requestBody+="]}"

            val jsonRequest = JsonObjectRequest(
                Request.Method.POST, url,jsonBody,
                Response.Listener {
                    Log.i("VOLLEY", it)
                    println(it)
                },
                Response.ErrorListener { error -> Log.e("VOLLEY", error.toString()) }) {
                override fun getBodyContentType(): String {
                    return "application/json; charset=utf-8"
                }

                @Throws(AuthFailureError::class)
                override fun getBody(): ByteArray? {
                    try {
                        return requestBody.toByteArray(charset("utf-8"))
                    } catch (uee: UnsupportedEncodingException) {
                        VolleyLog.wtf(
                            "Unsupported Encoding while trying to get the bytes of %s using %s",
                            requestBody,
                            "utf-8"
                        )
                        return null
                    }
                }

                override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
                    var responseString = ""
                    if (response != null) {
                    }
                    return Response.success(
                        responseString,
                        HttpHeaderParser.parseCacheHeaders(response)

                    )
                }
            }
            requestQueue.add(stringRequest)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }
}