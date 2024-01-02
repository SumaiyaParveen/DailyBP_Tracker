package com.dailybptracker

import com.dailybptracker.MainActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore  // Import FirebaseFirestore
import com.sumaiya.dailybptracker.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore  // Add a property for Firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize FirebaseApp
        FirebaseApp.initializeApp(this)

        // Initialize ViewBinding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize Firestore
        db = FirebaseFirestore.getInstance()

        // Set click listener for sign-in button
        binding.signinbtn.setOnClickListener {
            val email = binding.etEmailAddress.text.toString()
            val password = binding.etPassword.text.toString()
            signInUser(email, password)
        }

        // Set click listener for the copyright text (optional)
        binding.textView1.setOnClickListener {
            // Handle click on copyright text, if needed
        }
    }

    private fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign-in successful, navigate to the HomeActivity
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish() // Close the LoginActivity
                } else {
                    // If sign-in fails, display a message to the user.
                    // You can customize this message based on the specific failure reason.
                    binding.etEmailAddress.error = "Sign-in failed."
                    binding.etEmailAddress.requestFocus()
                }
            }
    }

}
