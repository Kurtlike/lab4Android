package com.kurtlike.lab4android

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dotInputButton.setOnClickListener {
            val intent = Intent(this, DataInputActivity::class.java)
            startActivity(intent)
        }
    }
}