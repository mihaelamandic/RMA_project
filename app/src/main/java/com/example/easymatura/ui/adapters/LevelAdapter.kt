package com.example.easymatura.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.easymatura.R
import com.example.easymatura.models.Level


class LevelAdapter : RecyclerView.Adapter<ItemViewHolder>() {
    val levels = mutableListOf<Level>()
    var onLevelSelectedListener : OnLevelSelectedListener? = null

    fun setLevels(levels: List<Level>) {
        this.levels.clear()
        this.levels.addAll(levels)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val level = levels[position]
        holder.bind(level)
        onLevelSelectedListener?.let { listener ->
            holder.itemView.setOnClickListener { listener.onLevelSelected(level) }
        }
    }

    override fun getItemCount(): Int = levels.count()
}


