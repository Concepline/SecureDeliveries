package com.example.securedeliveries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MenuSupervisor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_supervisor)
        crearrepartidor()
        eliminarrepartidor()
        elegirimagendescifrar()
        cerrarSesion()
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


    private fun eliminarrepartidor() {
        val btnEliminar = findViewById<ImageView>(R.id.btn_eliminar_repartidor)

        btnEliminar.setOnClickListener {
            startActivity(Intent(this@MenuSupervisor,EliminarRepartidor::class.java))
        }
    }

    private fun elegirimagendescifrar(){
        val btnElegirImagen = findViewById<ImageView>(R.id.btn_descifrar_imagen)

        btnElegirImagen.setOnClickListener {
            startActivity(Intent(this@MenuSupervisor,SeleccionDescifrar::class.java))
        }
    }
}