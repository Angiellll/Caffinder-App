package com.example.first_test

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FavoriteActivity  : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_favorite)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 取得底部的 ImageButton 元件
        val btnHome = findViewById<ImageButton>(R.id.btnHome)
        val btnSchedule = findViewById<ImageButton>(R.id.btnSchedule)
        val btnFavorite = findViewById<ImageButton>(R.id.btnFavorite)
        val btnNotification = findViewById<ImageButton>(R.id.btnNotification)
        val btnProfile = findViewById<ImageButton>(R.id.btnProfile)

        // 設定按鈕點擊事件
        btnHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        btnSchedule.setOnClickListener {
            startActivity(Intent(this, ScheduleActivity::class.java))
        }

        btnFavorite.setOnClickListener {
            Toast.makeText(this, "你已在收藏頁面", Toast.LENGTH_SHORT).show()
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