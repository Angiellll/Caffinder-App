package com.example.first_test

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment


class FilterDialog : AppCompatActivity() {
        private lateinit var spinnerCity: Spinner
        private lateinit var ratingWifi: RatingBar
        private lateinit var ratingSeat: RatingBar
        private lateinit var ratingQuiet: RatingBar
        private lateinit var ratingFood: RatingBar
        private lateinit var ratingCheap: RatingBar
        private lateinit var ratingMusic: RatingBar
        private lateinit var radioTimeLimit: RadioGroup
        private lateinit var radioSocket: RadioGroup
        private lateinit var radioStanding: RadioGroup
        private lateinit var btnSubmit: Button

        @SuppressLint("MissingInflatedId")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_filter_dialog)

            spinnerCity = findViewById(R.id.spinnerCity)
            ratingWifi = findViewById(R.id.ratingWifi)
            ratingSeat = findViewById(R.id.ratingSeat)
            ratingQuiet = findViewById(R.id.ratingQuiet)
            ratingFood = findViewById(R.id.ratingFood)
            ratingCheap = findViewById(R.id.ratingCheap)
            ratingMusic = findViewById(R.id.ratingMusic)
            radioTimeLimit = findViewById(R.id.radioTimeLimit)
            radioSocket = findViewById(R.id.radioSocket)
            radioStanding = findViewById(R.id.radioStanding)
            btnSubmit = findViewById(R.id.btnSubmit)

            // Spinner 設定
            val cityList = arrayOf("台北", "新北")
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cityList)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCity.adapter = adapter

            btnSubmit.setOnClickListener {
                submitForm()
            }
        }

    private fun submitForm() {
        val city = spinnerCity.selectedItem.toString()
        val wifi = ratingWifi.rating
        val seat = ratingSeat.rating
        val quiet = ratingQuiet.rating
        val food = ratingFood.rating
        val cheap = ratingCheap.rating
        val music = ratingMusic.rating

        val timeLimit = when (radioTimeLimit.checkedRadioButtonId) {
            R.id.radioTimeYes -> "Yes"
            R.id.radioTimeNo -> "No"
            else -> "未選擇"
        }
        val socket = when (radioSocket.checkedRadioButtonId) {
            R.id.radioSocketYes -> "Yes"
            R.id.radioSocketNo -> "No"
            else -> "未選擇"
        }
        val standing = when (radioStanding.checkedRadioButtonId) {
            R.id.radioStandingYes -> "Yes"
            R.id.radioStandingNo -> "No"
            else -> "未選擇"
        }

        val resultIntent = Intent().apply {
            putExtra("city", city)
            putExtra("wifi", wifi)
            putExtra("seat", seat)
            putExtra("quiet", quiet)
            putExtra("food", food)
            putExtra("cheap", cheap)
            putExtra("music", music)
            putExtra("timeLimit", timeLimit)
            putExtra("socket", socket)
            putExtra("standing", standing)
        }
        setResult(RESULT_OK, resultIntent)
        finish()  // 回去 HomeActivity
    }
}