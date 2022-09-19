package com.example.easymatura.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.easymatura.databinding.ItemBinding
import com.example.easymatura.models.Level

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(level: Level) {
        val binding = ItemBinding.bind(itemView)
        binding.btnItem.text = level.name
    }
}