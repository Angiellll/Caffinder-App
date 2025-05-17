package com.example.first_test

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class EditProfileActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val etName = findViewById<EditText>(R.id.etName)
        val etBirthday = findViewById<EditText>(R.id.etBirthday)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup) // 記得 RadioGroup 要設 id
        val btnSave = findViewById<Button>(R.id.btnSave)

        // 設定點擊生日欄彈出日期選擇器
        etBirthday.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                // 注意月份是從0開始，所以要 +1
                val birthdayStr =
                    String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
                etBirthday.setText(birthdayStr)
            }, year, month, day)
            dpd.show()
        }
            btnSave.setOnClickListener {
                val name = etName.text.toString()
                val birthday = etBirthday.text.toString()
                val phone = etPhone.text.toString()
                val email = etEmail.text.toString()

                val selectedGenderId = radioGroup.checkedRadioButtonId
                val gender = if (selectedGenderId != -1) {
                    findViewById<RadioButton>(selectedGenderId).text.toString()
                } else {
                    ""
                }

                val resultIntent = Intent().apply {
                    putExtra("name", name)
                    putExtra("birthday", birthday)
                    putExtra("phone", phone)
                    putExtra("email", email)
                    putExtra("gender", gender)
                }

                setResult(Activity.RESULT_OK, resultIntent)
                finish() // 關閉這個畫面，回到上一頁
            }
        }
}