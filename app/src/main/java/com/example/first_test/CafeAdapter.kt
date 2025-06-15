package com.example.first_test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CafeAdapter(
    private var cafes: MutableList<Cafe>,
    private val onItemClick: (Cafe) -> Unit
) : RecyclerView.Adapter<CafeAdapter.CafeViewHolder>() {

    inner class CafeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tvCafeName)
        val address = itemView.findViewById<TextView>(R.id.tvCafeAddress)

        fun bind(cafe: Cafe) {
            name.text = cafe.name
            address.text = cafe.address
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
