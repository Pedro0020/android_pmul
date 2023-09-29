package com.example.ajustessharedpreferences

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // en cualquier activity podemos acceder directamente a los ajustes
// en cualquier activity podemos acceder directamente a los ajustes
        // en cualquier activity podemos acceder directamente a los ajustes
        val ajustes = Ajustes.getInstance(this)
        val nombre = ajustes.nombre
    }
}