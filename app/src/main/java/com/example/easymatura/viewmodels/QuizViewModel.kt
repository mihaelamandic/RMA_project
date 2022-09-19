package com.example.easymatura.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easymatura.models.Exam
import com.example.easymatura.models.Level
import com.example.easymatura.models.Quiz
import com.example.easymatura.repository.FirestoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class QuizViewModel (
    val quizRepository: FirestoreRepository
) : ViewModel(), FirestoreRepository.OnFirestoreQuizComplete{

    private var app = this
    private var _quizes: MutableLiveData<List<Quiz>> = MutableLiveData()

    val quizes: LiveData<List<Quiz>>
        get() = _quizes

    fun getQuizData(level:Level, exam:Exam){
        viewModelScope.launch(Dispatchers.IO) {
            quizRepository.getAllQuestions(level, exam, app)
        }
    }

    override fun quizAdded(quizList: List<Quiz?>?) {
        _quizes.postValue(quizList as List<Quiz>?)
    }

}