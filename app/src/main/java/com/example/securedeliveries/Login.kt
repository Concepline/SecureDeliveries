package com.example.securedeliveries

import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat


class Login : AppCompatActivity() {

    //Variables de cancelación y autenticación
    private var cancellationSignal: CancellationSignal? = null
    private val authenticationCallback: BiometricPrompt.AuthenticationCallback


        //Mensajes de autenticación exitosa y error de autenticación
        get() =
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)
                    notificacionUsuario("Error de autenticación.")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    notificacionUsuario("Autenticación exitosa.")
                    startActivity(Intent(this@Login,TipoUsuario::class.java))
                }

            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Revisión de huella dactilar.
        revisionBiometrica()

        //Botón de imagen de huella digital.
        val btnHuella = findViewById<ImageView>(R.id.btnhuella)

        //Personzalicación de indicación del mensaje de la huella dactilar.
        btnHuella.setOnClickListener {
            val biometricPrompt = BiometricPrompt.Builder(this)
                .setTitle("Autenticación biometrica.")
                .setSubtitle("Autenticación requerida para iniciar aplicación.")
                .setDescription("Tipo de autenticación biometrica: huella dactilar.")
                .setNegativeButton("Cancelar",this.mainExecutor,DialogInterface.OnClickListener { dialogInterface, i ->
                    notificacionUsuario("Autenticación cancelada.")
                }).build()

            biometricPrompt.authenticate(getCancellationSignal(),mainExecutor,authenticationCallback)
        }

    }

    //Notificaciones de usuario.
    private fun notificacionUsuario(message: String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    //Cancelación de autenticación dactilar por parte del usuario.
    private fun getCancellationSignal() : CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            notificacionUsuario("Autenticación cancelada.")
        }
        return cancellationSignal as CancellationSignal
    }

    //Revisión de requisitos del hardware y software para iniciar la autenticación de la huella dactilar.
    private fun revisionBiometrica(): Boolean {
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        if (!keyguardManager.isKeyguardSecure) {
            notificacionUsuario("No existe ningún mecanismo de autenticación en el dispositivo.")
            return false
        }

        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED) {
            notificacionUsuario("La aplicación no cuenta con permisos de autenticación.")
            return false
        }
        return if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            true
        } else true
    }
}