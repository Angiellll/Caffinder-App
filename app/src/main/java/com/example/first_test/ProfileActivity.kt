package com.example.first_test
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.first_test.EditProfileActivity
import com.example.first_test.R
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {
    // 定義新的 launcher（用來接收 EditProfile 回傳的資料）
    private lateinit var editProfileLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        window.statusBarColor = Color.parseColor("#4c2812")
        window.navigationBarColor = Color.parseColor("#4c2812")

        val btnEditProfile = findViewById<Button>(R.id.btnEditProfile)

        // 🔹 顯示 Firebase 登入使用者的 email
        val currentUser = FirebaseAuth.getInstance().currentUser
        val emailFromFirebase = currentUser?.email
        findViewById<TextView>(R.id.txtEmail).text = "電子郵件：${emailFromFirebase ?: "尚未登入"}"

        // 🔹 註冊 Activity Result 處理函式
        editProfileLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val name = data?.getStringExtra("name")
                val birthday = data?.getStringExtra("birthday")
                val phone = data?.getStringExtra("phone")
                val gender = data?.getStringExtra("gender")

                // 顯示資料到畫面上（請確保 layout 有對應 id）
                findViewById<TextView>(R.id.txtName).text = "姓名：$name"
                findViewById<TextView>(R.id.txtBirthday).text = "生日：$birthday"
                findViewById<TextView>(R.id.txtPhone).text = "電話：$phone"
                findViewById<TextView>(R.id.txtGender).text = "性別：$gender"

                // ✅ 再次從 Firebase 拿 email 顯示
                val firebaseEmail = FirebaseAuth.getInstance().currentUser?.email
                findViewById<TextView>(R.id.txtEmail).text = "電子郵件：${firebaseEmail ?: "尚未登入"}"
            }
        }

        // 🔹 啟動 EditProfileActivity
        btnEditProfile.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            editProfileLauncher.launch(intent)
        }
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish() // 返回上一頁
        }
        val btnHome = findViewById<ImageButton>(R.id.btnHome)
        val btnSchedule = findViewById<ImageButton>(R.id.btnSchedule)
        val btnFavorite = findViewById<ImageButton>(R.id.btnFavorite)
        val btnNotification = findViewById<ImageButton>(R.id.btnNotification)
        val btnProfile = findViewById<ImageButton>(R.id.btnProfile)

        btnHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        btnSchedule.setOnClickListener {
            val intent = Intent(this, ScheduleActivity::class.java)
            startActivity(intent)
        }

        btnFavorite.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }

        btnNotification.setOnClickListener {
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }

        btnProfile.setOnClickListener {
            Toast.makeText(this, "你已在會員檔案", Toast.LENGTH_SHORT).show()
        }

        val btnLogout = findViewById<ImageButton>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            // Firebase 登出
            FirebaseAuth.getInstance().signOut()

            Toast.makeText(this, "已登出", Toast.LENGTH_SHORT).show()

            // 跳回登入頁面，並清除返回堆疊
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

    }
}
