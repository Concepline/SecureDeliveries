package com.example.securedeliveries

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginSupervisor : AppCompatActivity() {

    private lateinit var usuario: EditText
    private lateinit var password: EditText
    private lateinit var acceso: Button
    private lateinit var dbh: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_supervisor)

        acceso = findViewById(R.id.btn_login_supervisor)
        usuario = findViewById(R.id.usuario_supervisor)
        password = findViewById(R.id.password_supervisor)
        dbh = DBHelper(this)


        acceso.setOnClickListener {

            val usuariotexto = usuario.text.toString()
            val passwordtext = password.text.toString()

            if (TextUtils.isEmpty(usuariotexto) || TextUtils.isEmpty(passwordtext)) {
                Toast.makeText(this, "Rellene todos los campos.", Toast.LENGTH_SHORT).show()

            } else {
                if (usuariotexto=="admin" && passwordtext=="12345") {
                    Toast.makeText(this,"Acceso concedido.",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginSupervisor,MenuSupervisor::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Error en el usuario y/o contraseña.", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}