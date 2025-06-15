package com.example.first_test

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class CafeDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cafe_detail)

        val cafe = intent.getParcelableExtra<Cafe>("cafe")

        // 圖片顯示
        val imgCafe = findViewById<ImageView>(R.id.imgCafe)
        Glide.with(this)
            .load(cafe?.imageUrl)
            .placeholder(R.drawable.placeholder)  // 可自訂 loading 圖片
            .error(R.drawable.default_image) // 找不到時的預設圖片
            .into(imgCafe)

        // 基本資訊顯示
        findViewById<TextView>(R.id.tvCafeName).text = cafe?.name ?: "名稱未知"
        findViewById<TextView>(R.id.tvCafeAddress).text = cafe?.address ?: "地址未知"
        findViewById<TextView>(R.id.tvCafeWifi).text = "Wi-Fi 穩定度及速度：${cafe?.wifi ?: "未知"}"
        findViewById<TextView>(R.id.tvCafeOpenTime).text = "營業時間：${cafe?.openTime ?: "未知"}"
        findViewById<TextView>(R.id.tvCafeSeat).text = "座位舒適度：${cafe?.seat ?: "未知"}"
        findViewById<TextView>(R.id.tvCafeQuiet).text = "安靜程度：${cafe?.quiet ?: "未知"}"
        findViewById<TextView>(R.id.tvCafeTasty).text = "餐點美味程度：${cafe?.tasty ?: "未知"}"
        findViewById<TextView>(R.id.tvCafeCheap).text = "價格親民程度：${cafe?.cheap ?: "未知"}"
        findViewById<TextView>(R.id.tvCafeMusic).text = "店內音樂氛圍評分：${cafe?.music ?: "未知"}"
        findViewById<TextView>(R.id.tvCafeLimitedTime).text = "是否有限時（maybe：依人潮決定）：${cafe?.limitedTime ?: "未知"}"
        findViewById<TextView>(R.id.tvCafePower).text = "插座充足程度（maybe：部分座位有）：${cafe?.socket ?: "未知"}"
        findViewById<TextView>(R.id.tvCafeStanding).text = "站立桌：${cafe?.standingDesk ?: "未知"}"
        findViewById<TextView>(R.id.tvCafeMRT).text = "鄰近捷運站：${cafe?.mrt ?: "未知"}"
        findViewById<TextView>(R.id.tvCafeUrl).text = "網站：${cafe?.url ?: "無連結"}"
    }
}
