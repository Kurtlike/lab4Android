package com.kurtlike.lab5android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.kurtlike.lab5android.chart.DrawChart
import com.kurtlike.lab5android.datainput.DataInputActivity
import com.kurtlike.lab5android.datainput.Dot
import com.kurtlike.lab5android.localBuisness.GoodOldIO
import com.kurtlike.lab5android.localBuisness.Test
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val dots = ArrayList<Dot>()
    var islocal = true
    var isFunctionSolve = false
    var url ="192.168.88.254:8082"
    lateinit var serverRequests: ServerRequests
    lateinit var goodOldIO: GoodOldIO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        serverRequests = ServerRequests(this)
        goodOldIO = Test()
        dotInputButton.setOnClickListener {
            val intent = Intent(this, DataInputActivity::class.java)
            startActivity(intent)
        }

        solveFunc.setOnClickListener {
            if(!islocal){
                loadDots()
                serverRequests.getFunctionalDots("http://$url/lab5/getAnswer",dots) {
                    saveDotsForFuncDraw(it)
                    chartReload.performClick()
                }
                isFunctionSolve = true
            }
            else{
                loadDots()
                goodOldIO.setDotsForInterpolate(dots)
                saveDotsForFuncDraw(goodOldIO.getDotsForDraw())
                chartReload.performClick()
                isFunctionSolve = true
            }

        }
        xValueButton.setOnClickListener{
            if(!islocal) {
                if (isFunctionSolve) {
                    serverRequests.getXvalue(
                        "http://192.168.88.254:8082/lab5/getXValue",
                        xValue.text.toString().toDouble()
                    ) {
                        xValue.setText(it.toString())
                    }
                }
            }
            else{
                if (isFunctionSolve) {
                    xValue.setText(goodOldIO.getXValue(xValue.text.toString().toDouble()).toString())
                }
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
                val choose = resources.getStringArray(R.array.serverSelect)
                islocal = choose[selectedItemPosition] != "Сервер"
                if(choose[selectedItemPosition] == "Сервер"){
                    chooseUrl()
                }
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
    fun saveDotsForFuncDraw(dots: ArrayList<Dot>){
        val preferences =  getSharedPreferences("DotsForDrawLine", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        val gson = Gson()
        val set = HashSet<String>()
        editor.clear()
        dots.forEach {
            val jString = gson.toJson(it)
            set.add(jString)
        }
        editor.putStringSet("dotsForDrawLine", set)
        editor.commit()
    }
    fun chooseUrl(){
        var editText = EditText(this)
        editText.width = 600
        editText.setText(url)
        var button = Button(this)
        button.top = 0
        button.x = 600F
        button.text = "Ввод"
        button.setOnClickListener {
           url = editText.text.toString()
            chartContainer.removeView(button)
            chartContainer.removeView(editText)
        }
        chartContainer.addView(editText)
        chartContainer.addView(button)
    }

}