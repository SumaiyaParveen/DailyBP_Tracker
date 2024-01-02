package com.dailybptracker

import com.dailybptracker.MainActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.sumaiya.dailybptracker.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    // Declare the binding variable
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Initialize ViewBinding
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Set click listener for sign-up button
        binding.signupbtn.setOnClickListener {
            val email = binding.etSEmailAddress.text.toString()
            val password = binding.etSPassword.text.toString()
            val confirmPassword = binding.etSConfPassword.text.toString()

            if (password == confirmPassword) {
                signUpUser(email, password)
            } else {
                // Show an error if passwords do not match
                binding.etSConfPassword.error = "Passwords do not match"
                binding.etSConfPassword.requestFocus()
            }
        }

        // Set click listener for the copyright text (optional)
        binding.textView1.setOnClickListener {
            // Handle click on copyright text, if needed
        }
    }

    private fun signUpUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registration successful, navigate to the next activity (e.g., HomeActivity)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    // If registration fails, display a message to the user.
                    // You can customize this message based on the specific failure reason.
                    binding.etSEmailAddress.error = "Registration failed."
                    binding.etSEmailAddress.requestFocus()
                }
            }
    }
}
