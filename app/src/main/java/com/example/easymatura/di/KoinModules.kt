package com.example.easymatura.di

import com.example.easymatura.repository.FirestoreRepository
import com.example.easymatura.viewmodels.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val firestoreModule = module {
    single { FirestoreRepository() }
}

val viewmodelModule = module {
    viewModel { LevelViewModel(get()) }
    viewModel { ExamListViewModel(get()) }
    viewModel { QuizViewModel(get()) }
}
