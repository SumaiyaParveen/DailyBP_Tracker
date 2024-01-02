package com.dailybptracker

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.sumaiya.dailybptracker.R
import java.util.*

class RecordMedicationActivity : AppCompatActivity() {
    private var etDate: EditText? = null
    private var etMedication: EditText? = null
    private var etDose: EditText? = null
    private var radioGroup: RadioGroup? = null
    private var rbAM: RadioButton? = null
    private var rbNoon: RadioButton? = null
    private var rbBedtime: RadioButton? = null
    private var btnSave: Button? = null
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recordmedication)

        // Initialize views
        etDate = findViewById(R.id.etDate)
        etMedication = findViewById(R.id.etMedication)
        etDose = findViewById(R.id.etDose)
        radioGroup = findViewById(R.id.radioGroup)
        rbAM = findViewById(R.id.rbAM)
        rbNoon = findViewById(R.id.rbNoon)
        rbBedtime = findViewById(R.id.rbBedtime)
        btnSave = findViewById(R.id.btnSave)

        // Set click listener for the save button
        btnSave?.setOnClickListener(View.OnClickListener { view: View? -> saveMedicationRecord() })
    }

    private fun saveMedicationRecord() {
        // Retrieve user input from EditText fields
        val date = etDate?.text.toString()
        val medication = etMedication?.text.toString()
        val dose = etDose?.text.toString()

        // Validate input for non-empty values
        if (!date.isNullOrEmpty() && !medication.isNullOrEmpty() && !dose.isNullOrEmpty()) {
            // Get selected time from RadioGroup
            var selectedTime = ""
            when (radioGroup?.checkedRadioButtonId) {
                R.id.rbAM -> selectedTime = "AM"
                R.id.rbNoon -> selectedTime = "NOON"
                R.id.rbBedtime -> selectedTime = "BEDTIME"
            }

            // Validate that a time is selected
            if (!selectedTime.isNullOrEmpty()) {
                // Create a data structure to store medication record
                val medicationRecord = hashMapOf(
                    "date" to date,
                    "medication" to medication,
                    "dose" to dose,
                    "time" to selectedTime,
                    "timestamp" to System.currentTimeMillis()
                )

                // Get the current user's ID
                val userId = auth.currentUser?.uid
                if (userId != null) {
                    // Save the record to Firestore under the user's document
                    db.collection("users").document(userId)
                        .collection("medicationRecords")
                        .add(medicationRecord)
                        .addOnSuccessListener { documentReference: DocumentReference? ->
                            // Record saved successfully
                            // You can add additional logic or UI updates here
                            Toast.makeText(
                                applicationContext,
                                "Medication record saved successfully!",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish() // Close the activity after saving
                        }
                        .addOnFailureListener { exception: Exception? ->
                            // Handle the failure to save the record
                            // You can show an error message or log the exception

                            // Show an error message using Toast
                            Toast.makeText(
                                applicationContext,
                                "Failed to save medication record. Please try again.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                } else {
                    // Handle the case where a time is not selected
                    // You can show an error message to the user using Toast
                    Toast.makeText(
                        applicationContext,
                        "Please select a time for the medication.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                // Handle the case where any of the input fields is empty
                // You can show an error message to the user using Toast
                Toast.makeText(
                    applicationContext,
                    "Please fill in all the fields before saving.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
