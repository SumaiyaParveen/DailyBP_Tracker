package com.dailybptracker

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sumaiya.dailybptracker.R

class RecordBPActivity : AppCompatActivity() {

    private lateinit var sysEditText: EditText
    private lateinit var diaEditText: EditText
    private lateinit var pulseEditText: EditText
    private lateinit var saveButton: Button

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recordbp)

        // Initialize views
        sysEditText = findViewById(R.id.etSYS)
        diaEditText = findViewById(R.id.etDIA)
        pulseEditText = findViewById(R.id.etPulserate)
        saveButton = findViewById(R.id.recordbtn)

        // Set click listener for the save button
        saveButton.setOnClickListener {
            saveBloodPressureRecord()
        }
    }

    private fun saveBloodPressureRecord() {
        // Retrieve user input from EditText fields
        val sysInput = sysEditText.text.toString()
        val diaInput = diaEditText.text.toString()
        val pulseInput = pulseEditText.text.toString()

        // Validate input for non-empty values
        if (sysInput.isNotEmpty() && diaInput.isNotEmpty() && pulseInput.isNotEmpty()) {
            try {
                // Attempt to convert input to integers
                val sys = sysInput.toInt()
                val dia = diaInput.toInt()
                val pulse = pulseInput.toInt()

                // Create a data structure to store blood pressure record
                val bloodPressureRecord = hashMapOf(
                    "sys" to sys,
                    "dia" to dia,
                    "pulse" to pulse,
                    "timestamp" to System.currentTimeMillis()
                )

                // Get the current user's ID
                val userId = auth.currentUser?.uid

                if (userId != null) {
                    // Save the record to Firestore under the user's document
                    db.collection("users").document(userId)
                        .collection("bloodPressureRecords")
                        .add(bloodPressureRecord)
                        .addOnSuccessListener {
                            Toast.makeText(
                                applicationContext,
                                "Blood Pressure Recorded successfully!",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish() // Close the activity after saving
                        }
                        .addOnFailureListener { exception ->
                            Log.e("RecordBPActivity", "Error saving blood pressure record", exception)

                            // Show an error message to the user
                            Toast.makeText(
                                applicationContext,
                                "Failed to save blood pressure record. Please try again.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
            } catch (e: NumberFormatException) {
                // Handle the case where input is not a valid integer
                // Show an error message to the user
                Toast.makeText(
                    applicationContext,
                    "Invalid input. Please enter numeric values for SYS, DIA, and pulse rate.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            // Handle the case where any of the input fields is empty
            // Show an error message to the user
            Toast.makeText(
                applicationContext,
                "Please enter values for SYS, DIA, and pulse rate.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

