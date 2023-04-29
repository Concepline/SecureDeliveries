package com.example.securedeliveries

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        splashTimer()
    }
    private fun splashTimer() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@MainActivity,Login ::class.java)
            startActivity(intent)
            finish()
        }, 3000) // tiempo en milisegundos (3 segundos)
    }
}