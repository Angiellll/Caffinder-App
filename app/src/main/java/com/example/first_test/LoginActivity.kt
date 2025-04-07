import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.SharedPreferences
import com.example.first_test.MainActivity
import com.example.first_test.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 檢查是否已經登入
        val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            // 如果已登入，直接跳轉到主畫面
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()  // 關閉當前 LoginActivity，防止用戶回到登入畫面
        } else {
            // 如果沒有登入，顯示登入畫面（可以加上提示）
            // 你也可以根據需求這裡添加其他行為，或者延遲一段時間再跳轉
        }
    }
}
