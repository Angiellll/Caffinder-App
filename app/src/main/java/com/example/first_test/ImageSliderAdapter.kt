package com.example.first_test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ImageSliderAdapter(private val imageUrls: List<String>) : RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>() {

    // ViewHolder 用來抓每張圖的畫面元件
    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageItem)
    }

    // 建立 ViewHolder 並綁定 layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image_slider, parent, false) // 使用單張圖的 layout
        return ImageViewHolder(view)
    }

    // 綁定圖片資料，使用 Glide 載入圖片
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val url = imageUrls[position]
        if (url.isNullOrBlank()) {
            holder.imageView.setImageResource(R.drawable.default_image) // 預設圖
        } else {
            Glide.with(holder.imageView.context)
                .load(url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.imageView)
        }
    }


    // 總共要顯示幾張圖片
    override fun getItemCount(): Int {
        return imageUrls.size
    }
}
