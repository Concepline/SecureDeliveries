package com.example.securedeliveries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginSupervisor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_supervisor)
        menuSupervisor()
    }

    private fun menuSupervisor() {
        val btnSupervisor = findViewById<Button>(R.id.btn_login_supervisor)

        btnSupervisor.setOnClickListener {
            startActivity(Intent(this@LoginSupervisor,MenuSupervisor::class.java))
            finish()
        }
    }
}