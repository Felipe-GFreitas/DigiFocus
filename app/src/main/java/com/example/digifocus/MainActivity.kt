package com.example.digifocus

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var textViewTimer: TextView
    private lateinit var buttonStartPause: Button
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var imageView: ImageView
    private lateinit var progressBarXP: ProgressBar
    private lateinit var settingsButton: Button

    private var isTimerRunning = false
    private var timeLeftInMillis: Long = 1500000 // 25 minutes in milliseconds
    private var startTimeInMillis: Long = 1500000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewTimer = findViewById(R.id.textViewTimer)
        buttonStartPause = findViewById(R.id.buttonStartPause)
        imageView = findViewById(R.id.imageView)
        progressBarXP = findViewById(R.id.progressBarXP)
        settingsButton = findViewById(R.id.settingsButton)

        buttonStartPause.setOnClickListener {
            if (isTimerRunning) {
                pauseTimer()
            } else {
                startTimer()
            }
        }

        settingsButton.setOnClickListener {
            val intent = Intent(this, ConfigurationsActivity::class.java)
            startActivity(intent)
        }

        updateCountDownText()
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateCountDownText()
                updateProgressBar()
            }

            override fun onFinish() {
                isTimerRunning = false
                buttonStartPause.text = "Start"
                progressBarXP.progress = 100 // Set progress to full when timer finishes
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

    private fun updateProgressBar() {
        val progress = ((startTimeInMillis - timeLeftInMillis).toFloat() / startTimeInMillis * 100).toInt()
        progressBarXP.progress = progress
    }
}
