package com.kurtlike.lab4android.datainput

import android.content.Context
import android.text.InputType.TYPE_CLASS_NUMBER
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.*
import androidx.core.view.children
import androidx.core.view.get
import com.kurtlike.lab4android.R
import java.util.Map.entry


class  DotForm {
    fun addDotForm(form: androidx.gridlayout.widget.GridLayout, indexValue: String, xValue: Double?, yValue: Double?): LinearLayout {

        val dotForm = LinearLayout(form.context)
        val index = TextView(dotForm.context)
        index.width = 100
        index.text = indexValue

        val xform = EditText(dotForm.context)
        xform.width = 400
        xform.maxEms = 4
        xform.inputType =TYPE_CLASS_NUMBER
        if (xValue == null) xform.setText("")
        else xform.setText(xValue.toString())

        val yform = EditText(dotForm.context)
        yform.width = 400
        yform.maxEms = 4
        yform.inputType =TYPE_CLASS_NUMBER
        if (yValue == null) yform.setText("")
        else yform.setText(yValue.toString())

        val deleteButton = Button(dotForm.context)
        deleteButton.width = 20
        deleteButton.text = "X"
        deleteButton.setOnClickListener{
            deleteForm(indexValue, form)
        }
        dotForm.addView(index)
        dotForm.addView(xform)
        dotForm.addView(yform)
        dotForm.addView(deleteButton)
        return dotForm
    }
    fun deleteForm(index: String, form: androidx.gridlayout.widget.GridLayout){
        val scrollChild = form.findViewById<androidx.gridlayout.widget.GridLayout>(R.id.scrollChild)
        scrollChild.children.iterator().forEach {
            val group = it as ViewGroup?
            if (group != null) {
                val textView = group.get(0) as TextView
                if(textView.text == index){
                    (group.parent as ViewManager).removeView(group)
                }
            }
        }
    }
}