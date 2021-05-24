package com.kurtlike.lab4android.datainput

import android.app.Activity
import android.os.Bundle
import androidx.gridlayout.widget.GridLayout
import com.kurtlike.lab4android.R


class DataInputActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.data_input)
        val form = findViewById<GridLayout>(R.id.scrollChild)
        form.columnCount = 1
        val dotForm = DotForm()
        for(i in 1..20){
            form.addView(dotForm.createDotForm(this, i.toString()))
        }
    }
}