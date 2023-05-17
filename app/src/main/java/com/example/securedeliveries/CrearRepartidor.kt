package com.example.securedeliveries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class CrearRepartidor : AppCompatActivity() {

    private lateinit var nombre: EditText
    private lateinit var usuario: EditText
    private lateinit var password: EditText
    private lateinit var guardarbtn: Button
    private lateinit var db: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_repartidor)


        nombre= findViewById(R.id.nombretext)
        usuario = findViewById(R.id.usuariotext)
        password = findViewById(R.id.passwordtext)

        guardarbtn = findViewById(R.id.btn_guardar)

        db = DBHelper(this)

        guardarbtn.setOnClickListener {
            val nombretext =  nombre.text.toString()
            val usuariotext = usuario.text.toString()
            val paswordtext = password.text.toString()
            val savedata = db.inserdata(usuariotext,paswordtext)

            if (TextUtils.isEmpty(usuariotext) || TextUtils.isEmpty(paswordtext)){
                    Toast.makeText(this, "Complete todos los campos.", Toast.LENGTH_SHORT).show()
            }
            else{
                if (savedata==true){
                    Toast.makeText(this,"Registro exitoso.",Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext,MenuSupervisor::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this,"Ya existe este usuario.",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
