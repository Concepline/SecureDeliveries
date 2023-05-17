package com.example.securedeliveries

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import java.lang.Math.PI
import java.lang.Math.sin
import kotlin.random.Random

class RepartidorFotografia : AppCompatActivity() {

    private val REQUEST_CAMERA = 1002
    var foto: Uri? = null
    private lateinit var fotografia: ImageView
    private lateinit var cancelar: ImageView
    private lateinit var aceptar: ImageView
    private lateinit var des: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repartidor_fotografia)
        abreCamara_Click()
        otrafoto()
        resizeImage()
        des()
        cancelar()
    }

    fun des()
    {
        des = findViewById(R.id.btn_des)
        des.setOnClickListener {
            descifrar()
        }
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
            cifrar()
        }
    }

    private fun descifrar(){
        fotografia = findViewById(R.id.img_foto)

        val key1:Long = 10
        val r = 0.99 // Parámetro de control r E (0,1]
        val k = 10 // Número de iteraciones del mapa caótico (Mínimo 10 iteraciones más aleatorio)

        val drawable = fotografia.drawable as BitmapDrawable
        val bitmap =drawable.bitmap

        val pixels = getPixels(bitmap)
        val sortedPixels = decryptImage(pixels,key1,r, k)
        val decryptedBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        decryptedBitmap.setPixels(sortedPixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        fotografia.setImageBitmap(decryptedBitmap)
        println("terminno descifrado")
    }


    private fun decryptImage(sortedPixels: IntArray, seed: Long, r: Double, k: Int): IntArray {
        val nPixels = sortedPixels.size

        val random = Random(seed)
        val Xo = DoubleArray(nPixels) { random.nextDouble() }
        val Xn = DoubleArray(nPixels)
        val decryptedPixels = IntArray(nPixels)

        for (i in 0 until k) {
            for (j in 0 until nPixels) {
                Xn[j] = r * sin(PI * Xo[j])
            }
            Xo.indices.sortedBy { Xn[it] }.forEachIndexed { index, sortedIndex ->
                decryptedPixels[sortedIndex] = sortedPixels[index]
            }
            Xn.copyInto(Xo)
        }

        return decryptedPixels
    }
    private fun cifrar(){
        fotografia = findViewById(R.id.img_foto)

        val key1:Long = 10
        val r = 0.99 // Parámetro de control r E (0,1]
        val k = 10 // Número de iteraciones del mapa caótico (Mínimo 10 iteraciones más aleatorio)

        val drawable = fotografia.drawable as BitmapDrawable
        val bitmap =drawable.bitmap

        val pixels = getPixels(bitmap)
        val sortedPixels = encryptImage(pixels,key1, r, k)
        val encryptedBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        encryptedBitmap.setPixels(sortedPixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        fotografia.setImageBitmap(encryptedBitmap)

        val filename = "imagen_guardada.jpg"
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, filename)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        val resolver = contentResolver
        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        val drawablec = fotografia.drawable as BitmapDrawable
        val bitmapc =drawablec.bitmap

        try {
            imageUri?.let { uri ->
                resolver.openOutputStream(uri)?.use { outputStream ->
                    bitmapc.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    Toast.makeText(this, "Imagen guardada correctamente", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Error al guardar la imagen", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getPixels(bitmap: Bitmap): IntArray {
        val width = bitmap.width
        val height = bitmap.height
        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
        return pixels
    }

    private fun encryptImage(pixels: IntArray, seed: Long, r: Double, k: Int): IntArray {
        val nPixels = pixels.size

        val random = Random(seed)
        val Xo = DoubleArray(nPixels) { random.nextDouble() }
        val Xn = DoubleArray(nPixels)
        val sortedPixels = IntArray(nPixels)

        for (i in 0 until k) {
            for (j in 0 until nPixels) {
                Xn[j] = r * kotlin.math.sin(PI * Xo[j])
            }
            Xo.indices.sortedBy { Xn[it] }.forEachIndexed { index, sortedIndex ->
                sortedPixels[index] = pixels[sortedIndex]
            }
            Xn.copyInto(Xo)
        }

        return sortedPixels
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
        val camaraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(camaraIntent,REQUEST_CAMERA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CAMERA){
            val img_foto : ImageView = findViewById(R.id.img_foto)
            img_foto.setImageBitmap(data?.extras?.get("data") as Bitmap)
        }
    }
}