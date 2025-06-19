package com.example.first_test

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CafeAdapter(
    private var cafes: MutableList<Cafe>,
    private val context: Context,
    private val onItemClick: (Cafe) -> Unit
) : RecyclerView.Adapter<CafeAdapter.CafeViewHolder>() {

    inner class CafeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCafeName: TextView = itemView.findViewById(R.id.tvCafeName)
        val tvCafeAddress: TextView = itemView.findViewById(R.id.tvCafeAddress)
        val imgCafe: ImageView = itemView.findViewById(R.id.imgCafe)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cafe, parent, false)
        return CafeViewHolder(view)
    }

    override fun onBindViewHolder(holder: CafeViewHolder, position: Int) {
        val cafe = cafes[position]
        holder.tvCafeName.text = cafe.name
        holder.tvCafeAddress.text = cafe.address

        Glide.with(holder.itemView.context)
            .load(cafe.url)
            .placeholder(R.drawable.default_image)
            .error(R.drawable.default_image)
            .into(holder.imgCafe)

        holder.itemView.setOnClickListener { onItemClick(cafe) }
    }

    override fun getItemCount(): Int = cafes.size

    fun updateList(newList: List<Cafe>) {
        cafes.clear()
        cafes.addAll(newList)
        notifyDataSetChanged()
    }
}
