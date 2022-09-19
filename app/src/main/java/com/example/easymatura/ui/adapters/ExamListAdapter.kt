package com.example.easymatura.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.easymatura.R
import com.example.easymatura.models.Exam


class ExamListAdapter: RecyclerView.Adapter<ExamViewHolder>() {
    val exams = mutableListOf<Exam>()
    var onExamSelectedListener : OnExamSelectedListener? = null

    fun setExams(exams: List<Exam>) {
        this.exams.clear()
        this.exams.addAll(exams)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return ExamViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExamViewHolder, position: Int) {
        val exam = exams[position]
        holder.bind(exam)
        onExamSelectedListener?.let { listener ->
            holder.itemView.setOnClickListener { listener.onExamSelected(exam) }
        }
    }

    override fun getItemCount(): Int = exams.count()
}