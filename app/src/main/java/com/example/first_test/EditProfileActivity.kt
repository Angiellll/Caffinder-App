package com.example.first_test

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val etName = findViewById<EditText>(R.id.etName)
        val etBirthday = findViewById<EditText>(R.id.etBirthday)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val btnSave = findViewById<Button>(R.id.btnSave)

        val name = intent.getStringExtra("name") ?: ""
        val birthday = intent.getStringExtra("birthday") ?: ""
        val phone = intent.getStringExtra("phone") ?: ""
        val gender = intent.getStringExtra("gender") ?: ""

        etName.setText(name)
        etBirthday.setText(birthday)
        etPhone.setText(phone)

        when (gender) {
            "男" -> radioGroup.check(R.id.radio_male)
            "女" -> radioGroup.check(R.id.radio_female)
            "其他" -> radioGroup.check(R.id.radio_other)
        }

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
            Toast.makeText(this, "按鈕按下了", Toast.LENGTH_SHORT).show()
            Log.d("EditProfile", "按鈕按下了")  // 1. 按鈕被點擊

            val name = etName.text.toString()
            val birthday = etBirthday.text.toString()
            val phone = etPhone.text.toString()
            val gender = radioGroup.findViewById<RadioButton>(
                radioGroup.checkedRadioButtonId
            )?.text?.toString() ?: ""

            // 取得目前登入的使用者 UID
            val user = FirebaseAuth.getInstance().currentUser
            val uid = user?.uid
            // 建立 Firestore 實例
            val db = FirebaseFirestore.getInstance()

            if (uid != null) {
                // ✅ 建立要儲存的資料 Map
                val userData = hashMapOf(
                    "name" to name,
                    "birthday" to birthday,
                    "phone" to phone,
                    "gender" to gender,
                    "email" to user.email
                )
                Log.d("EditProfile", "開始寫入 Firestore")  // 2. 開始寫入雲端

                // 寫入 Firestore：以 uid 作為 document ID
                db.collection("users").document(uid)
                    .set(userData)
                    .addOnSuccessListener {
                        Log.d("EditProfile", "寫入成功，準備結束頁面")  // 3. Firestore 寫入成功
                        Toast.makeText(this, "資料已儲存至雲端！", Toast.LENGTH_SHORT).show()

                        val resultIntent = Intent().apply {
                            putExtra("name", name)
                            putExtra("birthday", birthday)
                            putExtra("phone", phone)
                            putExtra("gender", gender)
                        }
                        Log.d("EditProfile", "已設定 result OK")  // 4. 已設定結果
                        setResult(Activity.RESULT_OK, resultIntent)
                        finish()
                        Log.d("EditProfile", "結束頁面指令後")  // 5. 已呼叫 finish()
                    }
                    .addOnFailureListener { e ->
                        Log.d("EditProfile", "寫入失敗: ${e.message}")  // 6. 寫入失敗
                        Toast.makeText(this, "儲存失敗：${e.message}", Toast.LENGTH_LONG).show()
                    }
            }else {
                    Log.d("EditProfile", "使用者未登入，無法儲存")  // 7. 使用者沒登入
                    Toast.makeText(this, "使用者未登入，無法儲存", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
