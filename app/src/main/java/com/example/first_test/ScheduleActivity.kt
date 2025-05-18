package com.example.first_test

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ScheduleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(0, systemBarsInsets.top, 0, systemBarsInsets.bottom)
            insets
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        // 設定狀態欄和導航欄為透明
        window.statusBarColor = Color.parseColor("#4c2812") // 深棕色
        window.navigationBarColor = Color.parseColor("#4c2812") // 底部導覽列也改色

        // 🟡 確保 layout 有正確 padding，讓內容不被 status/navigation bar 擋到
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(0, systemBarsInsets.top, 0, systemBarsInsets.bottom)
            insets
        }


        val btnHome = findViewById<ImageButton>(R.id.btnHome)
        val btnSchedule = findViewById<ImageButton>(R.id.btnSchedule)
        val btnFavorite = findViewById<ImageButton>(R.id.btnFavorite)
        val btnNotification = findViewById<ImageButton>(R.id.btnNotification)
        val btnProfile = findViewById<ImageButton>(R.id.btnProfile)

        btnHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        btnSchedule.setOnClickListener {
            Toast.makeText(this, "你已在我的行程頁面", Toast.LENGTH_SHORT).show()
        }

        btnFavorite.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }

        btnNotification.setOnClickListener {
            startActivity(Intent(this, NotificationActivity::class.java))
        }

        btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish() // 返回上一頁
        }
    }
}