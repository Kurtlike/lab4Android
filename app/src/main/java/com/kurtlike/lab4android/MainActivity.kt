package com.kurtlike.lab4android

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import com.google.gson.Gson
import com.kurtlike.lab4android.chart.DrawChart
import com.kurtlike.lab4android.datainput.DataInputActivity
import com.kurtlike.lab4android.datainput.Dot
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val dots = ArrayList<Dot>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dotInputButton.setOnClickListener {
            val intent = Intent(this, DataInputActivity::class.java)
            startActivity(intent)
        }

        solveFunc.setOnClickListener {
            val preferences = getSharedPreferences("Dots", MODE_PRIVATE)
            val gson = Gson()
            dots.clear()
            preferences.getStringSet("dots", HashSet())?.forEach {
                dots.add(gson.fromJson(it, Dot::class.java))
            }
            chartReload.performClick()
        }
        chartReload.setOnClickListener {
            chartContainer.removeAllViews()
            val drawChart = DrawChart(this)
            drawChart.setSize(chartContainer.width, chartContainer.height)
            drawChart.setDots(dots)
            chartContainer.addView(drawChart)

        }

    }

}