package com.example.digifocus

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class ConfigurationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val spinnerTimeOptions: Spinner = findViewById(R.id.spinnerTimeOptions)
        val spinnerActivityOptions: Spinner = findViewById(R.id.spinnerActivityOptions)
        val applyButton: Button = findViewById(R.id.applyButton)

        val timeOptions = arrayOf("10:00", "15:00", "20:00", "25:00")
        val timeAdapter = ArrayAdapter(this, R.layout.spinner_item, timeOptions)
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTimeOptions.adapter = timeAdapter

        val activityOptions = arrayOf("Estudar", "Trabalhar", "Exercitar", "Ler")
        val activityAdapter = ArrayAdapter(this, R.layout.spinner_item, activityOptions)
        activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerActivityOptions.adapter = activityAdapter

        applyButton.setOnClickListener {
            val selectedTime = spinnerTimeOptions.selectedItem.toString()
            val selectedActivity = spinnerActivityOptions.selectedItem.toString()

            val sharedPreferences = getSharedPreferences("DigiFocusPrefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("timerTime", selectedTime)
            editor.putString("selectedActivity", selectedActivity)
            editor.apply()

            finish()
        }
    }
}
