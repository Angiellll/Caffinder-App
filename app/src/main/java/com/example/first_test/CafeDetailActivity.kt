package com.example.first_test

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class CafeDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cafe_detail)

        val cafe = intent.getParcelableExtra<Cafe>("cafe")
        if (cafe == null) {
            finish() // 沒有資料就關掉頁面，避免出錯
            return
        }

        val imgCafe = findViewById<ImageView>(R.id.imgCafe)
        Glide.with(this)
            .load(cafe.imageUrl)
            .placeholder(R.drawable.loading)
            .error(R.drawable.default_image)
            .into(imgCafe)

        val imgCafeUrl = findViewById<ImageView>(R.id.imgCafeUrl)
        if (cafe.url.isNullOrBlank()) {
            imgCafeUrl.visibility = View.GONE
        } else {
            imgCafeUrl.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(cafe.url))
                startActivity(intent)
            }
        }

        //店名
        findViewById<TextView>(R.id.tvCafeName).text = cafe.name ?: "名稱未知"
        // 地址
        findViewById<TextView>(R.id.tvCafeAddress).text = "${cafe.address ?: "地址未知"}"

        //營業時間（當資料庫欄位為Null或空字串時，隱藏該TextView）
        val tvOpenTime = findViewById<TextView>(R.id.tvCafeOpenTime)
        if (cafe.openTime.isNullOrBlank()) {
            tvOpenTime.visibility = View.GONE
        } else {
            tvOpenTime.text = "・營業時間：${cafe.openTime}"
        }

        //限時（當資料庫欄位為Null或空字串時，隱藏該TextView）
        val tvLimitedTime = findViewById<TextView>(R.id.tvCafeLimitedTime)
        if (cafe.limitedTime.isNullOrBlank()) {
            tvLimitedTime.visibility = View.GONE
        } else {
            tvLimitedTime.text = "・是否有限時：${cafe.limitedTime}"
        }

        //插座（當資料庫欄位為Null或空字串時，隱藏該TextView）
        val tvPower = findViewById<TextView>(R.id.tvCafePower)
        if (cafe.socket.isNullOrBlank()) {
            tvPower.visibility = View.GONE
        } else {
            tvPower.text = "・插座：${cafe.socket}"
        }

        //捷運站（當資料庫欄位為Null或空字串時，隱藏該TextView）
        val tvMRT = findViewById<TextView>(R.id.tvCafeMRT)
        if (cafe.mrt.isNullOrBlank()) {
            tvMRT.visibility = View.GONE
        } else {
            tvMRT.text = "・鄰近捷運站：${cafe.mrt}"
        }

        //以下為導覽列設定
        // 返回上一頁
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }
        // 下方導覽按鈕
        findViewById<ImageButton>(R.id.btnHome).setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        findViewById<ImageButton>(R.id.btnSchedule).setOnClickListener {
            startActivity(Intent(this, ScheduleActivity::class.java))
        }
        findViewById<ImageButton>(R.id.btnFavorite).setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }
        findViewById<ImageButton>(R.id.btnNotification).setOnClickListener {
            startActivity(Intent(this, NotificationActivity::class.java))
        }
        findViewById<ImageButton>(R.id.btnProfile).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}
