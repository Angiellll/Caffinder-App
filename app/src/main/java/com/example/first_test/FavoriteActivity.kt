package com.example.first_test

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FavoriteActivity : AppCompatActivity() {
    //收藏成功的咖啡廳資訊 （安吉新增的）
    private lateinit var recyclerView: RecyclerView
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var allCafes: List<Cafe>  // 所有咖啡廳完整資料

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_favorite)

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

        // 初始化 RecyclerView （安吉新增的）
        recyclerView = findViewById(R.id.recyclerViewFavorite)
        recyclerView.layoutManager = LinearLayoutManager(this)

        allCafes = CafeDataStore.getAllCafes()  // <--

        // 加入這段除錯，印出每筆咖啡廳id跟名字
        allCafes.forEach { cafe ->
            Log.d("FavoriteActivity", "onCreate allCafes id=${cafe.id} name=${cafe.name}")
        }

        // 取得已收藏的 ID 清單（安吉新增的）
        val favoriteIds = getFavoriteIds()
        Log.d("FavoriteActivity", "收藏IDs: $favoriteIds")
        // 過濾出已收藏的咖啡廳資料（安吉新增的）
        val favoriteCafes = allCafes.filter { cafe -> favoriteIds.contains(cafe.id) }.toMutableList()
        Log.d("FavoriteActivity", "收藏咖啡廳數量: ${favoriteCafes.size}")

        // 🔄 修改：只傳入點擊事件給 FavoriteAdapter（列表用 updateList 之後再設）
        favoriteAdapter = FavoriteAdapter { selectedCafe ->
            val intent = Intent(this, CafeDetailActivity::class.java)
            intent.putExtra("cafe", selectedCafe)
            startActivity(intent)
        }
        recyclerView.adapter = favoriteAdapter
        // 🟢 新增：將資料列表更新給 Adapter
        favoriteAdapter.updateList(favoriteCafes)

        // 設定底部按鈕
        setupBottomNav()
    }

    // 🔼 新增：每次回到畫面都更新收藏列表
    override fun onResume() {
        super.onResume()
        val updatedFavoriteIds = getFavoriteIds()
        Log.d("FavoriteActivity", "onResume 讀到的收藏IDs = $updatedFavoriteIds")

        //val updatedFavoriteCafes = allCafes.filter { updatedFavoriteIds.contains(it.id) }.toMutableList()
        val updatedFavoriteCafes = CafeDataStore.getAllCafes().filter { updatedFavoriteIds.contains(it.id) }.toMutableList()
        Log.d("FavoriteActivity", "篩選出來的咖啡廳數量 = ${updatedFavoriteCafes.size}")

        favoriteAdapter.updateList(updatedFavoriteCafes)
    }

    // 取得所有收藏的ID（安吉新增的）
    private fun getFavoriteIds(): Set<String> {
        val prefs = getSharedPreferences("favorite_prefs", Context.MODE_PRIVATE)
        return prefs.all.mapNotNull { if (it.value == true) it.key else null }.toSet()
    }

    // 新增 setupBottomNav() 函式，把底部導航收好（安吉新增的）
    private fun setupBottomNav() {
        val btnHome = findViewById<ImageButton>(R.id.btnHome)
        val btnSchedule = findViewById<ImageButton>(R.id.btnSchedule)
        val btnFavorite = findViewById<ImageButton>(R.id.btnFavorite)
        val btnNotification = findViewById<ImageButton>(R.id.btnNotification)
        val btnProfile = findViewById<ImageButton>(R.id.btnProfile)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)

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
        btnBack.setOnClickListener {
            finish()
        }
    }
}
