package com.example.first_test

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    // 宣告 Email 輸入欄位與按鈕元件
    private lateinit var edtEmail: EditText
    private lateinit var btnSendResetLink: Button

    // Firebase 認證物件
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // 取得 XML 中對應的元件
        edtEmail = findViewById(R.id.edtEmailOrUsername)
        btnSendResetLink = findViewById(R.id.btnSendResetLink)

        // 初始化 FirebaseAuth 實例
        auth = FirebaseAuth.getInstance()

        // 按下「發送重置密碼郵件」按鈕時的處理邏輯
        btnSendResetLink.setOnClickListener {
            // 取得使用者輸入的 Email，並去除前後空白
            val email = edtEmail.text.toString().trim()

            // 檢查是否為空值
            if (email.isEmpty()) {
                Toast.makeText(this, "請輸入 Email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener  // 結束事件處理
            }

            // 檢查 Email 格式是否正確
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "請輸入正確的 Email 格式", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 使用 Firebase 發送重設密碼的 Email
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // 成功時提示使用者
                        Toast.makeText(this, "已寄出重設密碼信件，請至信箱查看", Toast.LENGTH_LONG).show()
                        // 可選：自動關閉此畫面（回到登入頁）
                        // finish()
                    } else {
                        // 發送失敗時顯示錯誤訊息（通常是該信箱未註冊）
                        Toast.makeText(this, "錯誤：${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}