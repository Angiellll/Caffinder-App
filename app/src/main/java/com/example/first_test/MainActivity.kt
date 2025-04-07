package com.example.first_test

// 引入 LoginActivity
import LoginActivity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()  // 開啟邊緣設計

        setContentView(R.layout.activity_main)

        // 設置邊緣適配
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 取得畫面上的元件（帳號、密碼輸入框 和 登入按鈕）
        val edtAccount = findViewById<EditText>(R.id.edtAccount)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        // 設定登入按鈕的點擊事件
        btnLogin.setOnClickListener {
            val account = edtAccount.text.toString() // 取得輸入的帳號
            val password = edtPassword.text.toString() // 取得輸入的密碼

            // 檢查帳號或密碼是否為空
            if (account.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "帳號或密碼不可為空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 假設這裡有一個硬編碼的用戶資料進行檢查（這部分可以改成從資料庫或 API 驗證）
            val validUsername = "user@example.com"
            val validPassword = "password123"

            if (account == validUsername && password == validPassword) {
                // 登入成功，儲存登入狀態到 SharedPreferences
                val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                with(sharedPreferences.edit()) {
                    putBoolean("isLoggedIn", true)
                    apply()  // 使用 apply() 儲存資料
                }

                // 登入成功後，跳轉到 LoginActivity
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()  // 結束 MainActivity，避免使用者返回
            } else {
                // 如果帳號或密碼錯誤，顯示錯誤訊息
                Toast.makeText(this, "帳號或密碼錯誤", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
