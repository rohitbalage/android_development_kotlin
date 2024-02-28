package com.rrbofficial.androiduipracticekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.content.Intent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun goToUIComponents(view: View) {

        val buttonClick = findViewById<Button>(R.id.uicomponents)
        buttonClick.setOnClickListener {
            val intent = Intent(this, uiComponents::class.java)
            startActivity(intent)

        }
    }

}