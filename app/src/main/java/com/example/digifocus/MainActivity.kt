package com.example.digifocus
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageButton
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var textViewTimer: TextView
    private lateinit var buttonStartPause: Button
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var imageView: ImageView
    private lateinit var progressBarXP: ProgressBar
    private lateinit var settingsButton: ImageButton

    private var isTimerRunning = false
    private var timeLeftInMillis: Long = 1500000 // Default 25 minutes in milliseconds
    private var startTimeInMillis: Long = 1500000

    private val digimonImages = listOf(
        R.drawable.digimon_level1,
        R.drawable.digimon_level2,
        R.drawable.digimon_level3,
        R.drawable.digimon_level4
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewTimer = findViewById(R.id.textViewTimer)
        buttonStartPause = findViewById(R.id.buttonStartPause)
        imageView = findViewById(R.id.imageView)
        progressBarXP = findViewById(R.id.progressBarXP)
        settingsButton = findViewById(R.id.settingsButton)

        settingsButton.setOnClickListener {
            val intent = Intent(this, ConfigurationsActivity::class.java)
            startActivity(intent)
        }

        loadTimerSettings()

        buttonStartPause.setOnClickListener {
            if (isTimerRunning) {
                pauseTimer()
            } else {
                startTimer()
            }
        }

        updateCountDownText()
        updateProgressBar()
    }

    override fun onResume() {
        super.onResume()
        loadTimerSettings()
        updateCountDownText()
        updateProgressBar()
    }

    private fun loadTimerSettings() {
        val sharedPreferences = getSharedPreferences("DigiFocusPrefs", Context.MODE_PRIVATE)
        val timerTime = sharedPreferences.getString("timerTime", "25:00")
        val timeParts = timerTime?.split(":")
        if (timeParts != null && timeParts.size == 2) {
            val minutes = timeParts[0].toLong()
            val seconds = timeParts[1].toLong()
            startTimeInMillis = (minutes * 60 + seconds) * 1000
            timeLeftInMillis = startTimeInMillis
        }
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateCountDownText()
                updateProgressBar()
                updateDigimonImage()
            }

            override fun onFinish() {
                isTimerRunning = false
                buttonStartPause.text = "Start"
                progressBarXP.progress = 100
                updateDigimonImage()
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

    private fun updateDigimonImage() {
        val progress = progressBarXP.progress

        when {
            progress >= 75 -> imageView.setImageResource(digimonImages[3])
            progress >= 50 -> imageView.setImageResource(digimonImages[2])
            progress >= 25 -> imageView.setImageResource(digimonImages[1])
            else -> imageView.setImageResource(digimonImages[0])
        }
    }
}
