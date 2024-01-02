package com.dailybptracker

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sumaiya.dailybptracker.R

class SummaryActivity : AppCompatActivity() {

    private lateinit var bpSummaryTextView: TextView
    private lateinit var medicationSummaryTextView: TextView

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        // Initialize views
        bpSummaryTextView = findViewById(R.id.bpSummaryTextView)
        medicationSummaryTextView = findViewById(R.id.medicationSummaryTextView)

        // Load and display BP records
        loadAndDisplayBloodPressureRecords()

        // Load and display medication records
        loadAndDisplayMedicationRecords()
    }

    private fun loadAndDisplayBloodPressureRecords() {
        val userId = auth.currentUser?.uid

        if (userId != null) {
            db.collection("users").document(userId)
                .collection("bloodPressureRecords")
                .get()
                .addOnSuccessListener { result ->
                    val sb = StringBuilder()
                    for (document in result) {
                        val sys = document["sys"]
                        val dia = document["dia"]
                        val pulse = document["pulse"]
                        sb.append("SYS: $sys, DIA: $dia, Pulse: $pulse\n")
                    }
                    bpSummaryTextView.text = sb.toString()
                }
                .addOnFailureListener { exception ->
                    bpSummaryTextView.text = "Failed to load BP records."
                }
        }
    }

    private fun loadAndDisplayMedicationRecords() {
        val userId = auth.currentUser?.uid

        if (userId != null) {
            db.collection("users").document(userId)
                .collection("medicationRecords")
                .get()
                .addOnSuccessListener { result ->
                    val sb = StringBuilder()
                    for (document in result) {
                        val date = document["date"]
                        val medication = document["medication"]
                        val dose = document["dose"]
                        val time = document["time"]
                        sb.append("Date: $date, Medication: $medication, Dose: $dose, Time: $time\n")
                    }
                    medicationSummaryTextView.text = sb.toString()
                }
                .addOnFailureListener { exception ->
                    medicationSummaryTextView.text = "Failed to load medication records."
                }
        }
    }
}
