package com.example.securedeliveries

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CrearRepartidor : AppCompatActivity() {

    private lateinit var nombre: EditText
    private lateinit var usuario: EditText
    private lateinit var password: EditText
    private lateinit var guardarbtn: ImageView
    private lateinit var cancelarbtn: ImageView
    private lateinit var db: DBHelper

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_repartidor)


        nombre= findViewById(R.id.nombretext)
        usuario = findViewById(R.id.usuariotext)
        password = findViewById(R.id.passwordtext)

        guardarbtn = findViewById(R.id.btn_guardar)
        cancelarbtn = findViewById(R.id.btn_cancelar_crear)

        db = DBHelper(this)

        guardarbtn.setOnClickListener {
            val nombretext =  nombre.text.toString()
            val usuariotext = usuario.text.toString()
            val paswordtext = password.text.toString()
            val savedata = db.inserdata(usuariotext,nombretext,paswordtext)

            if (TextUtils.isEmpty(usuariotext) || TextUtils.isEmpty(paswordtext)){
                    Toast.makeText(this, "Complete todos los campos.", Toast.LENGTH_SHORT).show()
            }
            else{
                if (savedata){
                    Toast.makeText(this,"Registro exitoso.",Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext,MenuSupervisor::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this,"Ya existe este usuario.",Toast.LENGTH_SHORT).show()
                }
            }
        }


        cancelarbtn.setOnClickListener {
            val intent = Intent(applicationContext,MenuSupervisor::class.java)
            startActivity(intent)
            finish()
        }

    }

}
