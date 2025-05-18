package com.example.first_test

// 引入 LoginActivity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    // FirebaseAuth 物件
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login1)

        // 初始化 FirebaseAuth
        auth = FirebaseAuth.getInstance()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 取得畫面上的元件（帳號、密碼輸入框 和 登入按鈕）
        val edtAccount = findViewById<EditText>(R.id.edtAccount)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnForgotPassword = findViewById<Button>(R.id.btnForgotPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        // 設定登入按鈕的點擊事件
        btnLogin.setOnClickListener {
            val account = edtAccount.text.toString() // 取得輸入的帳號
            val password = edtPassword.text.toString() // 取得輸入的密碼

            // 檢查帳號或密碼是否為空
            if (account.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "帳號或密碼不可為空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 使用 Firebase Auth 進行登入
            auth.signInWithEmailAndPassword(account, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // 登入成功
                        Toast.makeText(this, "登入成功，歡迎回來！", Toast.LENGTH_SHORT).show()

                        // 跳轉至 HomeActivity
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // 登入失敗，顯示錯誤訊息
                        Toast.makeText(
                            this,
                            "登入失敗：${task.exception?.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

        // 忘記密碼按鈕點擊事件
        btnForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent) // 開啟 ForgotPasswordActivity
        }

        // 註冊按鈕點擊事件
        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent) // 開啟 RegisterActivity
        }
    }
}
