package com.example.first_test
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.first_test.EditProfileActivity
import com.example.first_test.R

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val btnEditProfile = findViewById<Button>(R.id.btnEditProfile)

        btnEditProfile.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivityForResult(intent, 100)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
            val name = data.getStringExtra("name")
            val birthday = data.getStringExtra("birthday")
            val phone = data.getStringExtra("phone")
            val email = data.getStringExtra("email")
            val gender = data.getStringExtra("gender")

            // 顯示在畫面上（請確保這些 TextView 有對應的 id）
            findViewById<TextView>(R.id.txtName).text = "姓名：$name"
            findViewById<TextView>(R.id.txtBirthday).text = "生日：$birthday"
            findViewById<TextView>(R.id.txtPhone).text = "電話：$phone"
            findViewById<TextView>(R.id.txtEmail).text = "電子郵件：$email"
            findViewById<TextView>(R.id.txtGender).text = "性別：$gender"
        }
    }
}
