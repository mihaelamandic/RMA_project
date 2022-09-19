package com.example.easymatura.ui.adapters

import com.example.easymatura.models.Exam
import com.example.easymatura.models.Level

interface OnLevelSelectedListener {
    fun onLevelSelected(level: Level)
}

interface OnExamSelectedListener{
    fun onExamSelected(exam: Exam)
}