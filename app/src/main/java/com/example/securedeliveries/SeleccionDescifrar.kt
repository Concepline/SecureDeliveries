package com.example.securedeliveries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView

class SeleccionDescifrar : AppCompatActivity() {

    private lateinit var galeria: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccion_descifrar)

        galeria = findViewById(R.id.galeria_supervisor)

        galeria.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode==1 && resultCode== RESULT_OK){
            galeria.setImageURI(data?.data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}