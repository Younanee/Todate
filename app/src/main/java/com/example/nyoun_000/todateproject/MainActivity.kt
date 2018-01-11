package com.example.nyoun_000.todateproject

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val initIntent = Intent(this, InitActivity::class.java)
        startActivity(initIntent)

    }
}
