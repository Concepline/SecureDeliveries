package com.example.securedeliveries

import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.Manifest
import android.app.Activity
import android.widget.Toast

class RepartidorFotografia : AppCompatActivity() {

    private val REQUEST_CAMERA = 1002
    var foto: Uri? = null
    private lateinit var fotografia: ImageView
    private lateinit var cancelar: ImageView
    private lateinit var aceptar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repartidor_fotografia)
        abreCamara_Click()
        otrafoto()
        resizeImage()
        cancelar()
    }

    fun resizeImage() {
        aceptar = findViewById(R.id.btn_aceptar)
        aceptar.setOnClickListener {
            fotografia = findViewById(R.id.img_foto)
            val newWidth = 1000 // Nueva anchura en píxeles
            val newHeight = 1000 // Nueva altura en píxeles
            val layoutParams = fotografia.layoutParams
            layoutParams.width = newWidth
            layoutParams.height = newHeight
            fotografia.layoutParams = layoutParams
            val width = fotografia.width // Obtener el ancho del ImageView en píxeles
            val height = fotografia.height // Obtener el alto del ImageView en píxeles

// Puedes imprimir o utilizar los valores obtenidos según tus necesidades
            println("Ancho del ImageView: $width")
            println("Alto del ImageView: $height")
        }
    }

    private fun cancelar() {
        cancelar = findViewById(R.id.btn_cancelar)
        cancelar.setOnClickListener {
            startActivity(Intent(this@RepartidorFotografia,MenuRepartidor::class.java))
            finish()
        }
    }

    private fun otrafoto() {
        fotografia = findViewById(R.id.img_foto)
        fotografia.setOnClickListener {
            abreCamara_Click()
        }
    }

    private fun abreCamara_Click(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val permisosCamara = arrayOf(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permisosCamara,REQUEST_CAMERA)
            }
            else{
                abreCamara()
            }
        } else {
            abreCamara()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            REQUEST_CAMERA ->{
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    abreCamara()
                }
                else {
                    Toast.makeText(applicationContext, "No puedes acceder a la cámara",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun abreCamara(){
        val value = ContentValues()
        value.put(MediaStore.Images.Media.TITLE,"Nueva imagen")
        foto = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,value)
        val camaraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        camaraIntent.putExtra(MediaStore.EXTRA_OUTPUT,foto)
        startActivityForResult(camaraIntent,REQUEST_CAMERA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CAMERA){
            val img_foto : ImageView = findViewById(R.id.img_foto)
            img_foto.setImageURI(foto)
        }
    }
}