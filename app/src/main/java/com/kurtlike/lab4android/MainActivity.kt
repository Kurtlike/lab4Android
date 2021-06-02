package com.kurtlike.lab4android

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import com.google.gson.Gson
import com.kurtlike.lab4android.chart.DrawChart
import com.kurtlike.lab4android.datainput.DataInputActivity
import com.kurtlike.lab4android.datainput.Dot
import com.kurtlike.lab4android.datainput.Presets
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val dots = ArrayList<Dot>()
    var islocal = true
    var isFunctionSolve = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dotInputButton.setOnClickListener {
            val intent = Intent(this, DataInputActivity::class.java)
            startActivity(intent)
        }

        solveFunc.setOnClickListener {
            loadDots()
            chartReload.performClick()
        }
        xValueButton.setOnClickListener{
            if(isFunctionSolve){

            }
        }
        chartReload.setOnClickListener {
            loadDots()
            val preferences = getSharedPreferences("Dots", MODE_PRIVATE)
            val gson = Gson()
            dots.clear()
            preferences.getStringSet("dots", HashSet())?.forEach {
                dots.add(gson.fromJson(it, Dot::class.java))
            }
            chartContainer.removeAllViews()
            val drawChart = DrawChart(this)
            drawChart.setSize(chartContainer.width, chartContainer.height)
            drawChart.setDots(dots)
            chartContainer.addView(drawChart)

        }


        val serverSelect = findViewById<Spinner>(R.id.serverSelect)
        val adapter = ArrayAdapter.createFromResource(this, R.array.serverSelect, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        serverSelect.adapter = adapter

        serverSelect.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View?, selectedItemPosition: Int, selectedId: Long
            ) {
                val choose = resources.getStringArray(R.array.presets)
                islocal = choose[selectedItemPosition] != "Сервер"
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
    fun loadDots(){
        val preferences = getSharedPreferences("Dots", MODE_PRIVATE)
        val gson = Gson()
        dots.clear()
        preferences.getStringSet("dots", HashSet())?.forEach {
            dots.add(gson.fromJson(it, Dot::class.java))
        }
    }


}