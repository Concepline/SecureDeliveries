package com.example.securedeliveries

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import kotlin.random.Random

class SeleccionDescifrar : AppCompatActivity() {

    private lateinit var galeria: ImageView
    private lateinit var btn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccion_descifrar)

        galeria = findViewById(R.id.galeria_supervisor)

        galeria.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,1)
        }

        descifrar()
    }


    private fun descifrar(){

        galeria = findViewById(R.id.galeria_supervisor)
        btn = findViewById(R.id.btn_descifra)
        btn.setOnClickListener {

        val key1:Long = 10
        val r = 0.99 // Parámetro de control r E (0,1]
        val k = 10 // Número de iteraciones del mapa caótico (Mínimo 10 iteraciones más aleatorio)

        val drawable = galeria.drawable as BitmapDrawable
        val bitmap =drawable.bitmap

        val pixels = getPixels(bitmap)
        val sortedPixels = decryptImage(pixels,key1,r, k)
        val decryptedBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        decryptedBitmap.setPixels(sortedPixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        galeria.setImageBitmap(decryptedBitmap)
        println("termino descifrado")
    }

    }
    private fun getPixels(bitmap: Bitmap): IntArray {
        val width = bitmap.width
        val height = bitmap.height
        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
        return pixels
    }

    private fun decryptImage(sortedPixels: IntArray, seed: Long, r: Double, k: Int): IntArray {
        val nPixels = sortedPixels.size

        val random = Random(seed)
        val Xo = DoubleArray(nPixels) { random.nextDouble() }
        val Xn = DoubleArray(nPixels)
        val decryptedPixels = IntArray(nPixels)

        for (i in 0 until k) {
            for (j in 0 until nPixels) {
                Xn[j] = r * Math.sin(Math.PI * Xo[j])
            }
            Xo.indices.sortedBy { Xn[it] }.forEachIndexed { index, sortedIndex ->
                decryptedPixels[sortedIndex] = sortedPixels[index]
            }
            Xn.copyInto(Xo)
        }

        return decryptedPixels
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode==1 && resultCode== RESULT_OK){
            galeria.setImageURI(data?.data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}