package com.dailybptracker


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.sumaiya.dailybptracker.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)  // Replace with your actual layout file

        // Find the buttons
        val recordBPButton: Button = findViewById(R.id.recordbpbtn)
        val recordMedicationButton: Button = findViewById(R.id.recordmedicationbtn)
        val summaryButton: Button = findViewById(R.id.summarybtn)
        val logoutButton: Button = findViewById(R.id.logoutbtn)

        // Set click listeners for the buttons
        recordBPButton.setOnClickListener {
            // Start the activity for recording blood pressure
            startActivity(Intent(this, RecordBPActivity::class.java))
        }

        recordMedicationButton.setOnClickListener {
            // Start the activity for recording medication
            startActivity(Intent(this, RecordMedicationActivity::class.java))
        }

        summaryButton.setOnClickListener {
            // Start the activity for viewing medication schedule
            startActivity(Intent(this, SummaryActivity::class.java))
        }
        logoutButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}

