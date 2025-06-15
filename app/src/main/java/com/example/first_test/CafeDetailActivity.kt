package com.example.first_test

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView


class CafeDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cafe_detail)

        val cafe = intent.getParcelableExtra<Cafe>("cafe")

        findViewById<TextView>(R.id.tvCafeName).text = cafe?.name
        findViewById<TextView>(R.id.tvCafeAddress).text = cafe?.address
        findViewById<TextView>(R.id.tvCafeWifi).text = "Wi-Fi 評分：${cafe?.wifi ?: "未知"}"
        findViewById<TextView>(R.id.tvCafeOpenTime).text = "營業時間：${cafe?.openTime ?: "未知"}"

// 你可以依需要顯示更多資訊

    }
}