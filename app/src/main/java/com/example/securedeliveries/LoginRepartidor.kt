package com.example.securedeliveries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginRepartidor : AppCompatActivity() {

    private lateinit var usuario: EditText
    private lateinit var password: EditText
    private lateinit var acceso: Button
    private lateinit var dbh: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_repartidor)
        menuRepartidor()
    }

    private fun menuRepartidor() {
        acceso = findViewById(R.id.btn_login_repartidor)
        usuario = findViewById(R.id.repartidor_usuario)
        password = findViewById(R.id.repartidor_password)
        dbh = DBHelper(this)

        acceso.setOnClickListener {

            val usuariotexto = usuario.text.toString()
            val passwordtext = password.text.toString()

            if (TextUtils.isEmpty(usuariotexto) || TextUtils.isEmpty(passwordtext)) {
                Toast.makeText(this, "Rellene todos los campos.", Toast.LENGTH_SHORT).show()
            }
            else{
                val checckuser = dbh.checkuserpass(usuariotexto,passwordtext)
                if(checckuser){
                    Toast.makeText(this,"Acceso concedido.",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginRepartidor,MenuRepartidor::class.java))
                    finish()
                }
                else
                {
                    Toast.makeText(this,"Error en usuario y/o contraseña.",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}