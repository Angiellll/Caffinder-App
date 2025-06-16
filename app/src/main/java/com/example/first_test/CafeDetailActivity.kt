package com.example.first_test

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import android.widget.Toast


class CafeDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cafe_detail) // 設定畫面為 cafe 詳細頁

        
        // 從前一頁 Intent 取得傳過來的咖啡廳資料（parcelable）
        @Suppress("DEPRECATION")
        val cafe = intent.getParcelableExtra("cafe") as? Cafe

        val textViewName = findViewById<TextView>(R.id.tvCafeName)
        val textViewAddress = findViewById<TextView>(R.id.tvCafeAddress)

        try {
            if (cafe != null) {
                textViewName.text = cafe.name ?: "名稱未知"
                textViewAddress.text = cafe.address ?: "地址未知"

                val imageUrls = listOfNotNull(
                    cafe.imageUrl.takeIf { !it.isNullOrBlank() },
                    "https://example.com/image2.jpg",
                    "https://example.com/image3.jpg"
                )

                val viewPager = findViewById<ViewPager2>(R.id.viewPager)
                viewPager.adapter = ImageSliderAdapter(imageUrls)

                findViewById<TextView>(R.id.tvCafeOpenTime).text = "營業時間：${cafe.openTime ?: "未知"}"
                findViewById<TextView>(R.id.tvCafeLimitedTime).text = "是否有限時（maybe：依人潮決定）：${cafe.limitedTime ?: "未知"}"
                findViewById<TextView>(R.id.tvCafePower).text = "插座充足程度（maybe：部分座位有）：${cafe.socket ?: "未知"}"
                findViewById<TextView>(R.id.tvCafeMRT).text = "鄰近捷運站：${cafe.mrt ?: "未知"}"

                val imgCafeUrl = findViewById<ImageView>(R.id.imgCafeUrl)
                imgCafeUrl.setOnClickListener {
                    cafe.url?.takeIf { it.isNotBlank() }?.let { url ->
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                    }
                }

            } else {
                Toast.makeText(this, "資料載入失敗", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "頁面載入發生錯誤", Toast.LENGTH_LONG).show()
        }

    }
}
