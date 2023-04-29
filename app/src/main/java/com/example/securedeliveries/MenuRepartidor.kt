package com.example.securedeliveries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class MenuRepartidor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_repartidor)
        tomarFoto()
        cerrarSesion()
    }

    private fun cerrarSesion() {
        val btnCerrar = findViewById<ImageView>(R.id.btn_cerrar_sesion_repartidor)

        btnCerrar.setOnClickListener {
            startActivity(Intent(this@MenuRepartidor,LoginRepartidor::class.java))
            finish()
        }
    }

    private fun tomarFoto() {
        val btnFoto = findViewById<ImageView>(R.id.btn_tomar_foto)

        btnFoto.setOnClickListener {
            startActivity(Intent(this@MenuRepartidor,RepartidorFotografia::class.java))
        }
    }
}


