package com.example.first_test

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FavoriteAdapter(
    private val cafes: MutableList<Cafe>,  // 這裡要改成 MutableList，因為後面會移除
    private val onItemClick: (Cafe) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgCafe: ImageView = itemView.findViewById(R.id.imgCafe)
        val tvCafeName: TextView = itemView.findViewById(R.id.tvCafeName)
        val btnLike: ImageButton = itemView.findViewById(R.id.btnLike)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val cafe = cafes[position]
        holder.tvCafeName.text = cafe.name
        Glide.with(holder.itemView)
            .load(cafe.imageUrl)
            .placeholder(R.drawable.loading)
            .into(holder.imgCafe)

        holder.itemView.setOnClickListener { onItemClick(cafe) }

        // 取消收藏邏輯
        holder.btnLike.setOnClickListener {
            val prefs = holder.itemView.context.getSharedPreferences("favorite_prefs", Context.MODE_PRIVATE)
            prefs.edit().remove(cafe.id).apply()

            // 從列表移除
            cafes.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, cafes.size)
        }
    }

    override fun getItemCount(): Int = cafes.size
}
