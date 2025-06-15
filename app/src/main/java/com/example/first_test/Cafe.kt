package com.example.first_test
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.google.gson.annotations.SerializedName
@Parcelize
data class Cafe(
    val id: String?,
    val name: String?,
    val city: String?,
    val wifi: Int?,
    val seat: Double?,
    val quiet: Int?,
    val tasty: Double?,
    val cheap: Double?,
    val music: Double?,
    val url: String?,
    val address: String?,
    val latitude: Double?,
    val longitude: Double?,
    val imageUrl: String?,  // 新增咖啡廳圖片網址欄位

    @SerializedName("limited_time")
    val limitedTime: String?,
    val socket: String?,

    @SerializedName("standing_desk")
    val standingDesk: String?,
    val mrt: String?,

    @SerializedName("open_time")
    val openTime: String?
): Parcelable
