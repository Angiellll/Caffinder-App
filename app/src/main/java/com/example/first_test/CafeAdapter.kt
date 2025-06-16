package com.example.first_test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CafeAdapter(
    private var cafes: MutableList<Cafe>,
    private val onItemClick: (Cafe) -> Unit

) : RecyclerView.Adapter<CafeAdapter.CafeViewHolder>() {

    inner class CafeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.tvCafeName)
        private val address: TextView = itemView.findViewById(R.id.tvCafeAddress)
        private val image: ImageView = itemView.findViewById(R.id.imgCafe)

        fun bind(cafe: Cafe) {
            name.text = cafe.name
            address.text = cafe.address

            Glide.with(itemView.context)
                .load(cafe.imageUrl)
                .placeholder(R.drawable.loading) // 載入中顯示的圖
                .error(R.drawable.error)             // 載入失敗顯示的圖
                .fallback(R.drawable.default_image)  // imageUrl 為 null 時顯示的圖（你可以自訂 default_image.png）
                .into(image)

            itemView.setOnClickListener { onItemClick(cafe) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cafe, parent, false)
        return CafeViewHolder(view)
    }

    override fun onBindViewHolder(holder: CafeViewHolder, position: Int) {
        holder.bind(cafes[position])
    }

    override fun getItemCount(): Int = cafes.size

    fun updateList(newList: List<Cafe>) {
        cafes.clear()
        cafes.addAll(newList)
        notifyDataSetChanged()
    }


}
