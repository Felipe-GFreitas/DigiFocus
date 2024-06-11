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
        val spinnerImageOptions: Spinner = findViewById(R.id.spinnerImageOptions)
        val applyButton: Button = findViewById(R.id.applyButton)

        val timeOptions = arrayOf("10:00", "15:00", "20:00", "25:00")
        val timeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, timeOptions)
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTimeOptions.adapter = timeAdapter

        val imageOptions = arrayOf("egg1", "egg2", "egg3", "egg4")
        val imageAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, imageOptions)
        imageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerImageOptions.adapter = imageAdapter

        applyButton.setOnClickListener {
            val selectedTime = spinnerTimeOptions.selectedItem.toString()
            val selectedImage = spinnerImageOptions.selectedItem.toString()

            val sharedPreferences = getSharedPreferences("DigiFocusPrefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("timerTime", selectedTime)
            editor.putString("selectedImage", selectedImage)
            editor.apply()

            finish()
        }
    }
}
