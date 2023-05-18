package com.example.securedeliveries

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class SeleccionDescifrar : AppCompatActivity() {

    private lateinit var galeria: ImageView
    private lateinit var btn: ImageView
    private lateinit var cancelarbtn: ImageView
    private lateinit var instrucion: TextView
    private lateinit var texto_btn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccion_descifrar)

        galeria = findViewById(R.id.galeria_supervisor)

        galeria.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,1)

            galeria = findViewById(R.id.galeria_supervisor)
            btn = findViewById(R.id.btn_descifra)
            texto_btn = findViewById(R.id.text_btn)

            btn.setImageResource(R.drawable.descifrar)
            texto_btn.setText(R.string.descifrar)
        }

        descifrar()
        cancelar()
    }


    private fun descifrar(){

            galeria = findViewById(R.id.galeria_supervisor)
            btn = findViewById(R.id.btn_descifra)
            texto_btn = findViewById(R.id.text_btn)

            val verificacion = galeria.drawable
            val textoverificaguardar = texto_btn.text.toString()


                btn.setOnClickListener {
                    if (verificacion != galeria.drawable)
                    {
                        if (textoverificaguardar!=texto_btn.text.toString())
                        {
                            val filename = "imagen-descifrada.png"
                            val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(
                                Date()
                            )
                            val filenameWithDate = "${currentDate}_$filename"
                            val contentValues = ContentValues().apply {
                                put(MediaStore.Images.Media.DISPLAY_NAME, filenameWithDate)
                                put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                            }

                            val resolver = contentResolver
                            val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                            val drawablec = galeria.drawable as BitmapDrawable
                            val bitmapc =drawablec.bitmap

                            try {
                                imageUri?.let { uri ->
                                    resolver.openOutputStream(uri)?.use { outputStream ->
                                        bitmapc.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                                        Toast.makeText(this, "Imagen guardada correctamente", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } catch (e: IOException) {
                                e.printStackTrace()
                                Toast.makeText(this, "Error al guardar la imagen", Toast.LENGTH_SHORT).show()
                            }
                            Toast.makeText(this, "Guardar imagen.", Toast.LENGTH_SHORT).show()
                        }
                        else{
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
                            instrucion = findViewById(R.id.txt_instruccion_descifrado)
                            instrucion.setText("Imagen descifrada.")
                            println("termino descifrado")
                            btn.setImageResource(R.drawable.guardar)
                            texto_btn.setText(R.string.guardar)
                            }
                        }
                    else
                    {
                        Toast.makeText(this, "Selecciona una imagen a descifrar.", Toast.LENGTH_SHORT).show()
                    }
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


    private fun cancelar(){
        cancelarbtn = findViewById(R.id.btn_cancelar_descifrar)
        cancelarbtn.setOnClickListener {
            val intent = Intent(applicationContext, MenuSupervisor::class.java)
            startActivity(intent)
            finish()
        }
    }
}