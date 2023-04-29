package com.example.securedeliveries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class LoginRepartidor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_repartidor)
        menuRepartidor()
    }

    private fun menuRepartidor() {
        val btnSupervisor = findViewById<Button>(R.id.btn_login_repartidor)

        btnSupervisor.setOnClickListener {
            startActivity(Intent(this@LoginRepartidor,MenuRepartidor::class.java))
            finish()
        }
    }
}