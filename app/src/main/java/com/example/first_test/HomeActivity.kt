package com.example.first_test

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeActivity : AppCompatActivity() {

    private lateinit var locationManager: LocationManager   // 用來取得裝置位置的系統服務
    private lateinit var locationListener: LocationListener // 位置更新時會觸發的監聽器

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        // 設定全螢幕時的邊距，避免內容被狀態列遮住
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 取得 ImageButton 按鈕
        val btnLocation = findViewById<ImageButton>(R.id.btnLocation)

        // 建立 LocationManager 服務
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        // 建立位置變更監聽器
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                val latitude = location.latitude     // 抓到的經度
                val longitude = location.longitude   // 抓到的緯度

                // 組合 Google Maps 查詢附近咖啡廳的網址
                val gmmIntentUri = Uri.parse("geo:$latitude,$longitude?q=coffee")

                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)  // 建立跳轉 Intent
                mapIntent.setPackage("com.google.android.apps.maps")     // 指定用 Google Maps 開啟
                startActivity(mapIntent)  // 執行跳轉地圖畫面

                // 只抓一次就好 → 抓到位置後就停止更新
                locationManager.removeUpdates(this)
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }

        // 當使用者點按圖示
        btnLocation.setOnClickListener {
            // 檢查使用者是否已經授權位置權限
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // 如果已經授權，就請求位置更新（0 秒/0 公尺觸發一次）
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 0L, 0f, locationListener
                )
            } else {
                // 如果沒授權，就申請權限（會跳出系統視窗讓使用者點選）
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1  // 這個 requestCode 隨便設定，只要唯一即可
                )
            }
        }
    }

    // 使用者授權結果會在這裡回傳
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // 檢查是否是剛剛申請的位置權限，且已授權
        if (requestCode == 1 && grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            // 如果已授權，就開始抓位置
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 0L, 0f, locationListener
                )
            }
        } else {
            // 如果拒絕權限，可以顯示 Toast 或 Dialog 告訴使用者需要開啟定位才能使用此功能
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 畫面關閉時停止位置更新，避免耗電
        if (::locationManager.isInitialized && ::locationListener.isInitialized) {
            locationManager.removeUpdates(locationListener)
        }
    }
}
