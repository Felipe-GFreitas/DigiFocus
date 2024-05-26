package com.example.digifocus

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var textViewTimer: TextView
    private lateinit var buttonStartPause: Button
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var spinnerTimeOptions: Spinner
    private lateinit var spinnerImageOptions: Spinner
    private lateinit var imageView: ImageView

    private var isTimerRunning = false
    private var timeLeftInMillis: Long = 1500000
    private var startTimeInMillis: Long = 1500000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewTimer = findViewById(R.id.textViewTimer)
        buttonStartPause = findViewById(R.id.buttonStartPause)
        spinnerTimeOptions = findViewById(R.id.spinnerTimeOptions)
        spinnerImageOptions = findViewById(R.id.spinnerImageOptions)
        imageView = findViewById(R.id.imageView)

        val timeOptions = arrayOf("10:00", "15:00", "20:00", "25:00")
        val timeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, timeOptions)
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTimeOptions.adapter = timeAdapter

        spinnerTimeOptions.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedTime = timeOptions[position]
                updateTime(selectedTime)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        buttonStartPause.setOnClickListener {
            if (isTimerRunning) {
                pauseTimer()
            } else {
                startTimer()
            }
        }

        updateCountDownText()

        val imageOptions = arrayOf("patamon", "digimoncorno", "greymon", "pumpmon")
        val imageAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, imageOptions)
        imageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerImageOptions.adapter = imageAdapter

        spinnerImageOptions.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedImage = imageOptions[position]
                updateImage(selectedImage)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun updateTime(selectedTime: String) {
        val timeInMinutes = selectedTime.substringBefore(":").toInt()
        val timeInMillis = timeInMinutes * 60000L

        timeLeftInMillis = timeInMillis
        startTimeInMillis = timeInMillis
        updateCountDownText()
    }

    private fun updateImage(selectedImage: String) {
        val resId = resources.getIdentifier(selectedImage, "drawable", packageName)
        imageView.setImageResource(resId)
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                isTimerRunning = false
                buttonStartPause.text = "Start"
            }
        }.start()

        isTimerRunning = true
        buttonStartPause.text = "Pause"
    }

    private fun pauseTimer() {
        countDownTimer.cancel()
        isTimerRunning = false
        buttonStartPause.text = "Start"
    }

    private fun updateCountDownText() {
        val minutes = (timeLeftInMillis / 1000) / 60
        val seconds = (timeLeftInMillis / 1000) % 60

        val timeFormatted = String.format("%02d:%02d", minutes, seconds)
        textViewTimer.text = timeFormatted
    }
}
