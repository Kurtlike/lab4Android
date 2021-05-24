package com.kurtlike.lab4android.datainput

import android.content.Context
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

class  DotForm {
    fun createDotForm(context: Context, indexValue: String): LinearLayout {
        val dotForm = LinearLayout(context)
        val index = TextView(dotForm.context)
        index.text = indexValue
        val xform = EditText(dotForm.context)
        val yform = EditText(dotForm.context)
        val deleteButton = Button(dotForm.context)
        dotForm.addView(index)
        dotForm.addView(xform)
        dotForm.addView(yform)
        dotForm.addView(deleteButton)
        return dotForm
    }
}