package com.example.first_test

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FavoriteAdapter(
    private val onItemClick: (Cafe) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val cafeList = mutableListOf<Cafe>()  // 🔄 修改：將原本傳入的 list 改為內部管理
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val cafe = cafeList[position]  // 🔄 使用內部管理的 list

        holder.bind(cafe)
        holder.itemView.setOnClickListener { onItemClick(cafe) }
        // 設定按鈕點擊事件
        holder.btnLike.setOnClickListener {
            val prefs = holder.itemView.context.getSharedPreferences("favorite_prefs", Context.MODE_PRIVATE)
            val isFavorite = prefs.getBoolean(cafe.id, false)
            if (!isFavorite) {
                // 加入收藏
                prefs.edit().putBoolean(cafe.id, true).apply()
                Toast.makeText(holder.itemView.context, "已加入收藏", Toast.LENGTH_SHORT).show()
                holder.btnLike.setImageResource(R.drawable.ic_heart_filled)  // 根據你的圖示切換
            } else {
                // 取消收藏
                prefs.edit().remove(cafe.id).apply()
                Toast.makeText(holder.itemView.context, "已取消收藏", Toast.LENGTH_SHORT).show()
                holder.btnLike.setImageResource(R.drawable.ic_heart_outline)
            }
        }
    }

    override fun getItemCount(): Int = cafeList.size  // 🔄 使用內部管理的 list

    // 🔼 新增：支援動態更新列表
    fun updateList(newList: List<Cafe>) {
        cafeList.clear()
        cafeList.addAll(newList)
        notifyDataSetChanged()
    }
    // ✅ ViewHolder：記得把按鈕/圖片/文字都對應正確
    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgCafe: ImageView = itemView.findViewById(R.id.imgCafe)
        private val tvName: TextView = itemView.findViewById(R.id.tvCafeName)
        //private val tvAddress: TextView = itemView.findViewById(R.id.tvFavoriteCafeAddress)
        val btnLike: ImageButton = itemView.findViewById(R.id.btnLike)

        fun bind(cafe: Cafe) {
            tvName.text = cafe.name ?: "無名稱"
            //tvAddress.text = cafe.address ?: "無地址"
            Glide.with(itemView.context)
                .load(cafe.imageUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.default_image)
                .into(imgCafe)
        }
    }
}
