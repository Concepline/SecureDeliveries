package com.example.securedeliveries

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class EliminarRepartidor : AppCompatActivity() {

    private lateinit var usuario: EditText
    private lateinit var eliminarbtn: ImageView
    private lateinit var db: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eliminar_repartidor)

        usuario = findViewById(R.id.usuario_eliminar)

        eliminarbtn = findViewById(R.id.btn_eliminar_usuario)

        db = DBHelper(this)

        eliminarbtn.setOnClickListener {
            val usuariotexto = usuario.text.toString()
            val deleteuser = db.deleteUser(usuariotexto)

            if (TextUtils.isEmpty(usuariotexto)){
                Toast.makeText(this, "Complete todos los campos.", Toast.LENGTH_SHORT).show()
            }
            else{
                if (deleteuser){
                    Toast.makeText(this, "Usuario eliminado correctamente.", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else {
                    Toast.makeText(this,"No se encontro el usuario.",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}