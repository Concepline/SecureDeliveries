package com.example.securedeliveries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MenuSupervisor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_supervisor)
        cerrarSesion()
        crearrepartidor()
    }

    private fun cerrarSesion() {
        val btnCerrar = findViewById<ImageView>(R.id.btn_cerrar_sesion_supervisor)

        btnCerrar.setOnClickListener {
            startActivity(Intent(this@MenuSupervisor,LoginSupervisor::class.java))
            finish()
        }
    }

    private fun crearrepartidor() {
        val btnCrear = findViewById<ImageView>(R.id.btn_crear_repartidor)

        btnCrear.setOnClickListener {
            startActivity(Intent(this@MenuSupervisor,CrearRepartidor::class.java))
        }
    }
}