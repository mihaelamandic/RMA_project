package com.example.easymatura.repository

import com.example.easymatura.models.Exam
import com.example.easymatura.models.Level
import com.example.easymatura.models.Quiz
import com.google.firebase.firestore.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class FirestoreRepository() {
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private var allLevels = ArrayList<Level>()
    private var allExams = ArrayList<Exam>()
    private var allQuestions = ArrayList<Quiz>()

    suspend fun getAllLevels(onFirestoreTaskComplete: OnFirestoreTaskComplete){
        withContext(Dispatchers.IO) {
            try {
                firebaseFirestore.collection("Matura").get()
                    .addOnCompleteListener {results ->
                        for (document in results.result){
                            var level = Level( document.id.toLong(), document.data.getValue("name").toString(), document.data.getValue("questions").toString())
                            allLevels.add(level)
                        }
                        onFirestoreTaskComplete.dataAdded(allLevels)
                        allLevels = ArrayList<Level>()
                    }
                    .addOnFailureListener {
                    }
            } catch (e: Exception) {
            }
        }
    }

    suspend fun getAllExams(onFirestoreExamComplete: OnFirestoreExamComplete, level: Level){
        withContext(Dispatchers.IO) {
            try {
                firebaseFirestore.collection("Matura").document(level.id.toString()).collection("Years").get()
                    .addOnCompleteListener {results ->
                        for (document in results.result){
                            var exams = Exam( document.id.toLong(), document.data.getValue("name").toString())
                            allExams.add(exams)
                        }
                        onFirestoreExamComplete.examAdded(allExams)
                        allExams = ArrayList<Exam>()
                    }
                    .addOnFailureListener {
                    }
            } catch (e: Exception) {
            }
        }
    }


    suspend fun getAllQuestions(level: Level, exam:Exam, onFirestoreQuizComplete: OnFirestoreQuizComplete){
        withContext(Dispatchers.IO) {
            try {
                firebaseFirestore.collection("Matura").document(level.id.toString())
                    .collection("Years").document(exam.id.toString())
                    .collection("Questions").get()
                    .addOnCompleteListener {results ->
                        for (document in results.result){
                            var questions = Quiz( document.id.toLong(),
                                document.data.getValue("question").toString(),
                                document.data.getValue("option_a").toString(),
                                document.data.getValue("option_b").toString(),
                                document.data.getValue("option_c").toString(),
                                document.data.getValue("option_d").toString(),
                                document.data.getValue("answer").toString())
                            allQuestions.add(questions)
                        }
                        onFirestoreQuizComplete.quizAdded(allQuestions)
                       allQuestions = ArrayList<Quiz>()
                    }
                    .addOnFailureListener {
                    }
            } catch (e: Exception) {
            }
        }
    }

    interface OnFirestoreTaskComplete {
        fun dataAdded(levelList: List<Level?>?)
    }

    interface OnFirestoreExamComplete{
        fun examAdded(examList: List<Exam?>?)
    }

    interface OnFirestoreQuizComplete{
        fun quizAdded(quizList: List<Quiz?>?)
    }
}