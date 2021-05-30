package com.kurtlike.lab4android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.kurtlike.lab4android.datainput.DataInputActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dotInputButton.setOnClickListener {
            val intent = Intent(this, DataInputActivity::class.java)
            startActivity(intent)
        }

        solveFunc.setOnClickListener {
            val preferences =  getSharedPreferences("Dots", Context.MODE_PRIVATE)
            val gson = Gson()
            var str = ""

            preferences.getStringSet("dots", HashSet())?.forEach {
                val dot = gson.fromJson(it, Dot::class.java)
                str += dot.index.toString() + " " + dot.x + " " + dot.y + "\n"
            }
            println(str)
        }
    }
}