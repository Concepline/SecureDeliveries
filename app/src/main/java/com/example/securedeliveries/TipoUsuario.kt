package com.example.securedeliveries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class TipoUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tipo_usuario)
        loginRepartidor()
        loginSupervisor()

    }



    private fun loginSupervisor() {
        val btnSupervisor = findViewById<ImageView>(R.id.btn_supervisor)

        btnSupervisor.setOnClickListener {
            startActivity(Intent(this@TipoUsuario,LoginSupervisor::class.java))
        }
    }


    private fun loginRepartidor() {
        val btnRepartidor = findViewById<ImageView>(R.id.btn_repartido)

        btnRepartidor.setOnClickListener {
            startActivity(Intent(this@TipoUsuario,LoginRepartidor::class.java))
        }
    }
}