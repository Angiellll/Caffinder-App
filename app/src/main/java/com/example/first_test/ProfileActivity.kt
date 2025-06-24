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
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log

class ProfileActivity : AppCompatActivity() {
    // 定義新的 launcher（用來接收 EditProfile 回傳的資料）
    private lateinit var editProfileLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        window.statusBarColor = Color.parseColor("#4c2812")
        window.navigationBarColor = Color.parseColor("#4c2812")

        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid
        Log.d("ProfileActivity", "目前登入者 UID：$uid")
        if (uid != null) {
            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val name = document.getString("name") ?: ""
                        val birthday = document.getString("birthday") ?: ""
                        val phone = document.getString("phone") ?: ""
                        val gender = document.getString("gender") ?: ""
                        val email = document.getString("email") ?: ""

                        // ✅ 顯示在畫面上
                        findViewById<TextView>(R.id.txtName).text = "姓名：$name"
                        findViewById<TextView>(R.id.txtBirthday).text = "生日：$birthday"
                        findViewById<TextView>(R.id.txtPhone).text = "電話：$phone"
                        findViewById<TextView>(R.id.txtGender).text = "性別：$gender"
                        findViewById<TextView>(R.id.txtEmail).text = "電子郵件：$email"

                        Log.d("ProfileActivity", "讀取成功：$name, $birthday, $phone, $gender, $email")
                    } else {
                        Log.d("ProfileActivity", "文件不存在")
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "讀取資料失敗：${e.message}", Toast.LENGTH_SHORT).show()
                    Log.e("ProfileActivity", "讀取失敗：", e) // 🔧
                }
        }

        val btnEditProfile = findViewById<Button>(R.id.btnEditProfile)

        // 🔹 顯示 Firebase 登入使用者的 email
        //val currentUser = FirebaseAuth.getInstance().currentUser
        //val emailFromFirebase = currentUser?.email
       // findViewById<TextView>(R.id.txtEmail).text = "電子郵件：${emailFromFirebase ?: "尚未登入"}"

        // 🔹 註冊 Activity Result 處理函式
        editProfileLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                // 🔹 原本畫面上已經顯示的資料（用來補空欄位）
                val originalName = findViewById<TextView>(R.id.txtName).text.toString().removePrefix("姓名：")
                val originalBirthday = findViewById<TextView>(R.id.txtBirthday).text.toString().removePrefix("生日：")
                val originalPhone = findViewById<TextView>(R.id.txtPhone).text.toString().removePrefix("電話：")
                val originalGender = findViewById<TextView>(R.id.txtGender).text.toString().removePrefix("性別：")

                // 🔹 儲存結果頁面回傳的資料（如果沒填，保留原本的）
                val name = data?.getStringExtra("name")?.takeIf { it.isNotBlank() } ?: originalName
                val birthday = data?.getStringExtra("birthday")?.takeIf { it.isNotBlank() } ?: originalBirthday
                val phone = data?.getStringExtra("phone")?.takeIf { it.isNotBlank() } ?: originalPhone
                val gender = data?.getStringExtra("gender")?.takeIf { it.isNotBlank() } ?: originalGender

                // 顯示資料到畫面上（請確保 layout 有對應 id）
                findViewById<TextView>(R.id.txtName).text = "姓名：$name"
                findViewById<TextView>(R.id.txtBirthday).text = "生日：$birthday"
                findViewById<TextView>(R.id.txtPhone).text = "電話：$phone"
                findViewById<TextView>(R.id.txtGender).text = "性別：$gender"

                // ✅ 再次從 Firebase 拿 email 顯示
               // val firebaseEmail = FirebaseAuth.getInstance().currentUser?.email
               // findViewById<TextView>(R.id.txtEmail).text = "電子郵件：${firebaseEmail ?: "尚未登入"}"
            }
        }

        // 🔹 啟動 EditProfileActivity
        btnEditProfile.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            // 傳遞目前的值（避免下次空白）
            intent.putExtra("name", findViewById<TextView>(R.id.txtName).text.toString().removePrefix("姓名："))
            intent.putExtra("birthday", findViewById<TextView>(R.id.txtBirthday).text.toString().removePrefix("生日："))
            intent.putExtra("phone", findViewById<TextView>(R.id.txtPhone).text.toString().removePrefix("電話："))
            intent.putExtra("gender", findViewById<TextView>(R.id.txtGender).text.toString().removePrefix("性別："))
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
