package com.example.first_test

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.google.gson.annotations.SerializedName

/**
 * 資料類別：Cafe
 * 代表一間咖啡廳的完整資料，用於顯示在咖啡廳列表與詳細頁面中。
 * 此類別支援 Parcelable，可在 Activity/Fragment 之間傳遞。
 */
@Parcelize
data class Cafe(
    val id: String?,             // 咖啡廳唯一識別碼（可能來自資料庫或後端 API）
    val name: String?,           // 咖啡廳名稱
    val city: String?,           // 所在城市名稱

    val wifi: Int?,              // Wi-Fi 評分（通常為整數，如 1~5）
    val seat: Double?,           // 座位數或舒適度評分（浮點數）

    val quiet: Int?,             // 安靜程度評分（整數，值越大代表越安靜）
    val tasty: Double?,          // 餐點好吃程度評分（浮點數）
    val cheap: Double?,          // 價格親民程度評分（浮點數）
    val music: Double?,          // 音樂氛圍評分（浮點數）

    val url: String?,            // 咖啡廳官網或詳細介紹的連結
    val address: String?,        // 咖啡廳地址

    val latitude: Double?,       // 緯度（地圖定位用）
    val longitude: Double?,      // 經度（地圖定位用）

    val imageUrl: String?,       // 📸 新增欄位：圖片網址，從 Firebase Storage 或後端取得的圖片連結

    @SerializedName("limited_time")
    val limitedTime: String?,   // 是否有時間限制（例如 2 小時內需離開）

    val socket: String?,        // 是否有插座（例如 "yes", "no", "few"）

    @SerializedName("standing_desk")
    val standingDesk: String?,  // 是否有站立式桌子（例如 "yes", "no"）

    val mrt: String?,           // 鄰近捷運站名稱

    @SerializedName("open_time")
    val openTime: String?       // 營業時間（例如 "10:00 - 21:00"）

    //要加低消跟其他最終資料庫欄位！！！！！！
) : Parcelable
