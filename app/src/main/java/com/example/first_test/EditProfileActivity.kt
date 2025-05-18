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
import com.google.firebase.auth.FirebaseAuth
import java.util.Calendar

class EditProfileActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val etName = findViewById<EditText>(R.id.etName)
        val etBirthday = findViewById<EditText>(R.id.etBirthday)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val btnSave = findViewById<Button>(R.id.btnSave)

        // 設定 email（從 Firebase 取得，不可編輯）
        val currentUser = FirebaseAuth.getInstance().currentUser
        etEmail.setText(currentUser?.email ?: "無法取得")
        etEmail.isEnabled = false

        // 日期選擇器
        etBirthday.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, y, m, d ->
                etBirthday.setText(String.format("%04d-%02d-%02d", y, m + 1, d))
            }, year, month, day).show()
        }

        btnSave.setOnClickListener {
            val name = etName.text.toString()
            val birthday = etBirthday.text.toString()
            val phone = etPhone.text.toString()
            val gender = radioGroup.findViewById<RadioButton>(
                radioGroup.checkedRadioButtonId
            )?.text?.toString() ?: ""

            val resultIntent = Intent().apply {
                putExtra("name", name)
                putExtra("birthday", birthday)
                putExtra("phone", phone)
                putExtra("gender", gender)
            }

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}