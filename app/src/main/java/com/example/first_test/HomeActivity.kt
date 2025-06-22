package com.example.first_test

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.gson.Gson


class HomeActivity : AppCompatActivity() {

    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CafeAdapter
    private lateinit var editTextSearch: EditText

    private var fullCafeList: List<Cafe> = emptyList()

    // 篩選的元件
    private lateinit var filterLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        Log.d("TestLog", "HomeActivity 啟動囉～")

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

        window.navigationBarColor = Color.parseColor("#4c2812")

        // 找元件
        recyclerView = findViewById(R.id.recyclerViewCafes)
        editTextSearch = findViewById(R.id.editTextSearch)

        // 設定 RecyclerView 為網格顯示
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        // 取得資料並設置 Adapter
        RetrofitClient.apiService.getCafes().enqueue(object : Callback<List<Cafe>> {
            override fun onResponse(call: Call<List<Cafe>>, response: Response<List<Cafe>>) {
                if (response.isSuccessful && response.body() != null) {
                    fullCafeList = response.body()!!
                    adapter = CafeAdapter(fullCafeList.toMutableList(), this@HomeActivity) { selectedCafe ->
                        val intent = Intent(this@HomeActivity, CafeDetailActivity::class.java)
                        intent.putExtra("cafe", selectedCafe)
                        startActivity(intent)
                    }
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this@HomeActivity, "載入失敗", Toast.LENGTH_SHORT).show()
                }
            }


            override fun onFailure(call: Call<List<Cafe>>, t: Throwable) {
                Toast.makeText(this@HomeActivity, "無法連線", Toast.LENGTH_SHORT).show()
            }
        })

        // 搜尋功能
        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                val filteredList = fullCafeList.filter {
                    it.name?.contains(query, ignoreCase = true) == true ||
                            it.address?.contains(query, ignoreCase = true) == true
                }
                adapter.updateList(filteredList)
            }
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })

        // 篩選功能
        val btnFilter = findViewById<ImageButton>(R.id.btnFilter)
        btnFilter.setOnClickListener {
            val intent = Intent(this, FilterDialog::class.java)
            filterLauncher.launch(intent)
        }
        // 接收從 FilterActivity 回傳的資料
        filterLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                if (data != null) {
                    val city = data.getStringExtra("city") ?: ""
                    val wifi = data.getFloatExtra("wifi", 0f)
                    val seat = data.getFloatExtra("seat", 0f)
                    val quiet = data.getFloatExtra("quiet", 0f)
                    val food = data.getFloatExtra("food", 0f)
                    val cheap = data.getFloatExtra("cheap", 0f)
                    val music = data.getFloatExtra("music", 0f)
                    val timeLimit = data.getStringExtra("timeLimit") ?: ""
                    val socket = data.getStringExtra("socket") ?: ""
                    val standing = data.getStringExtra("standing") ?: ""

                    applyFilters(city, wifi, seat, quiet, food, cheap, music, timeLimit, socket, standing)
                }
            }
        }

        // 地圖定位
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                val latitude = location.latitude
                val longitude = location.longitude
                val gmmIntentUri = Uri.parse("geo:$latitude,$longitude?q=coffee")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
                locationManager.removeUpdates(this)
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }

        val btnLocation = findViewById<ImageButton>(R.id.btnLocation)
        btnLocation.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, locationListener)
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }
        }

        // 下方導覽按鈕
        findViewById<ImageButton>(R.id.btnHome).setOnClickListener {
            Toast.makeText(this, "你已在主頁", Toast.LENGTH_SHORT).show()
        }
        findViewById<ImageButton>(R.id.btnSchedule).setOnClickListener {
            startActivity(Intent(this, ScheduleActivity::class.java))
        }
        findViewById<ImageButton>(R.id.btnFavorite).setOnClickListener {
            if (fullCafeList.isEmpty()) {
                Toast.makeText(this, "資料尚未載入完成", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this, FavoriteActivity::class.java)
            intent.putParcelableArrayListExtra("all_cafes", ArrayList(fullCafeList)) // <- 這裡改成 Parcelable 傳遞
            startActivity(intent)
        }

        findViewById<ImageButton>(R.id.btnNotification).setOnClickListener {
            startActivity(Intent(this, NotificationActivity::class.java))
        }
        findViewById<ImageButton>(R.id.btnProfile).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, locationListener)
            }
        } else {
            Toast.makeText(this, "需要開啟定位權限才能使用此功能", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::locationManager.isInitialized && ::locationListener.isInitialized) {
            locationManager.removeUpdates(locationListener)
        }
    }

    //根據篩選條件過濾咖啡館列表
    private fun applyFilters(
        city: String,
        wifi: Float,
        seat: Float,
        quiet: Float,
        food: Float,
        cheap: Float,
        music: Float,
        timeLimit: String,
        socket: String,
        standing: String
    ) {
        val filteredList = fullCafeList.filter { cafe ->
            (city.isEmpty() || cafe.city?.contains(city, ignoreCase = true) == true) &&
                    (wifi == 0f || (cafe.wifi ?: 0) >= wifi.toInt()) &&
                    (seat == 0f || (cafe.seat ?: 0.0) >= seat.toDouble()) &&
                    (quiet == 0f || (cafe.quiet ?: 0) >= quiet.toInt()) &&
                    (food == 0f || (cafe.tasty ?: 0.0) >= food.toDouble()) &&
                    (cheap == 0f || (cafe.cheap ?: 0.0) >= cheap.toDouble()) &&
                    (music == 0f || (cafe.music ?: 0.0) >= music.toDouble()) &&
                    (timeLimit == "未選擇" || cafe.limitedTime == timeLimit) &&
                    (socket == "未選擇" || cafe.socket == socket) &&
                    (standing == "未選擇" || cafe.standingDesk == standing)
        }
        adapter.updateList(filteredList)
    }

}
