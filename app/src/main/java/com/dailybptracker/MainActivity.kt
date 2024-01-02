package com.dailybptracker

import com.dailybptracker.LoginActivity
import com.dailybptracker.RegisterActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.sumaiya.dailybptracker.R
import android.content.Intent
import android.widget.Button
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)  // Make sure to replace with your actual layout file
        FirebaseApp.initializeApp(this)

        // Find the "Login" and "Register" buttons
        val loginButton: Button = findViewById(R.id.loginbtn)
        val registerButton: Button = findViewById(R.id.registerbtn)
        val db = Firebase.firestore
        // Set click listeners for the buttons
        loginButton.setOnClickListener {
            // Start the LoginActivity when the "Login" button is clicked
            startActivity(Intent(this, LoginActivity::class.java))
        }

        registerButton.setOnClickListener {
            // Start the RegisterActivity when the "Register" button is clicked
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}