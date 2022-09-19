package com.example.easymatura.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easymatura.models.Level
import com.example.easymatura.repository.FirestoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LevelViewModel(
    val levelRepository: FirestoreRepository
) : ViewModel(), FirestoreRepository.OnFirestoreTaskComplete{

    private var app = this
    private var _levels: MutableLiveData<List<Level>> = MutableLiveData()
    val levels: LiveData<List<Level>>
        get() = _levels

    fun getLevels() {
        viewModelScope.launch(Dispatchers.IO) {
            levelRepository.getAllLevels(app)

        }
    }
    override fun dataAdded(levelList: List<Level?>?) {
        _levels.postValue(levelList as List<Level>?)
    }
}
