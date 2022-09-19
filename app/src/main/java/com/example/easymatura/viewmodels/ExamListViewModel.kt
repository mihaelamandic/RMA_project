package com.example.easymatura.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easymatura.models.Exam
import com.example.easymatura.models.Level
import com.example.easymatura.repository.FirestoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ExamListViewModel(
    val examRepository: FirestoreRepository
) : ViewModel(), FirestoreRepository.OnFirestoreExamComplete{

    private var app = this
    private var _exams: MutableLiveData<List<Exam>> = MutableLiveData()
    val exams: LiveData<List<Exam>>
        get() = _exams


    fun getExams(level: Level) {
        viewModelScope.launch(Dispatchers.IO) {
            examRepository.getAllExams(app, level)
        }
    }
     override fun examAdded(examList: List<Exam?>?) {
        _exams.postValue(examList as List<Exam>?)
    }
}