package com.example.easymatura.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.easymatura.databinding.ItemBinding
import com.example.easymatura.models.Exam

class ExamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(exam: Exam) {
        val binding = ItemBinding.bind(itemView)
        binding.btnItem.text = exam.name
    }
}