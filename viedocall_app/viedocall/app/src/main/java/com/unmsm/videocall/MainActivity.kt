package com.unmsm.videocall

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    //Prmiso de uso de la camara y audio
    private val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
    private val requestcode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("MainActivity", "onCreate")

        if (!isPermissionGranted()) {
            askPermissions()
        }
        // Inicializamos la base datos del Firebase
        FirebaseApp.initializeApp(this)

        //Boton de logeo y el campo para digitar el texto
        val loginBtn: Button = findViewById(R.id.loginBtn)
        val usernameEdit: EditText = findViewById(R.id.usernameEdit)

        loginBtn.setOnClickListener {
            val username = usernameEdit.text.toString()
            Log.d("MainActivity", "Login button clicked with username: $username")

            val intent = Intent(this, CallActivity::class.java)
            intent.putExtra("username", username)
            Log.d("MainActivity", "Starting CallActivity")
            startActivity(intent)
        }
    }

    //Preguntar al usuario para otorgar los permisos
    private fun askPermissions() {
        Log.d("MainActivity", "Asking for permissions")
        ActivityCompat.requestPermissions(this, permissions, requestcode)
    }

    // Función para verificar que los permiso esten otorgados
    private fun isPermissionGranted(): Boolean {
        Log.d("MainActivity", "Checking permissions")
        permissions.forEach {
            if (ActivityCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED) {
                Log.d("MainActivity", "Permission not granted: $it")
                return false
            }
        }
        return true
    }
}
