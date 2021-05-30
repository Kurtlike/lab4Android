package com.kurtlike.lab4android.datainput

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.core.view.children
import androidx.core.view.get
import androidx.gridlayout.widget.GridLayout
import com.google.gson.Gson
import com.kurtlike.lab4android.Dot
import com.kurtlike.lab4android.R


class DataInputActivity: Activity() {
    var dotCounter = 0
    private val dotList = ArrayList<Dot>()
    private val dotForm = DotForm()
    lateinit var form: GridLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.data_input)
        form = findViewById<GridLayout>(R.id.scrollChild)
        form.columnCount = 1

        val presets = Presets()
        val presetSpinner = findViewById<Spinner>(R.id.presetSpinner)

        val adapter = ArrayAdapter.createFromResource(this, R.array.presets, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        presetSpinner.adapter = adapter

        presetSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View?, selectedItemPosition: Int, selectedId: Long
            ) {
                val choose = resources.getStringArray(R.array.presets)
               presets.presets.forEach {
                   val name = it.name
                   if (name == choose[selectedItemPosition]) pastPresets(it)
               }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        val addDotButton = findViewById<Button>(R.id.addDotButton)
        addDotButton.setOnClickListener {
            val dot = dotForm.addDotForm(form, dotCounter.toString(),"".toDoubleOrNull(), "".toDoubleOrNull())
            dotCounter++
            form.addView(dot)
        }
        val clearButton = findViewById<Button>(R.id.clearButton)
        clearButton.setOnClickListener{
            form.removeAllViews()
            dotCounter = 0
        }
    }
    fun pastPresets(preset: Presets.Preset){
        dotList.clear()
        form.removeAllViews()
        preset.dots.forEach {
            form.addView(dotForm.addDotForm(form, it.index.toString(), it.x, it.y))
            dotList.add(it)
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        val form = findViewById<GridLayout>(R.id.scrollChild)
        val scrollChild = form.findViewById<androidx.gridlayout.widget.GridLayout>(R.id.scrollChild)
        scrollChild.children.iterator().forEach {
            val group = it as ViewGroup?
            if (group != null) {
                val textIndex = group.get(0) as TextView
                val textX = group.get(1) as EditText
                val textY = group.get(2) as EditText
                val index = textIndex.text.toString().toInt()
                val x: Double
                val y: Double
                x = if(textX.text.toString() == "") 0.0
                else textX.text.toString().toDouble()

                y = if(textY.text.toString() == "") 0.0
                else textY.text.toString().toDouble()

                val dot = Dot(index, x, y)
                dotList.add(dot)
            }
        }
        outState.putSerializable("dotList", dotList)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val newDotList = savedInstanceState.getSerializable("dotList") as java.util.ArrayList<Dot>
        form.removeAllViews()
        dotList.addAll(newDotList)
        dotList.forEach {
            val nextDot = dotForm.addDotForm(form, it.index.toString(), it.x, it.y)
            dotList.add(it)
            form.addView(nextDot)
            dotCounter++
        }
    }
    override fun onBackPressed() {
        val preferences =  getSharedPreferences("Dots", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        val gson = Gson()
        val set = HashSet<String>()
        dotList.forEach {
            val jString = gson.toJson(it)
            set.add(jString)
        }
        editor.putStringSet("dots", set)
        editor.commit()
        super.onBackPressed()
    }
}