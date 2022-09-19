package com.example.easymatura.models

import com.google.firebase.firestore.DocumentId
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Exam (
    @DocumentId
    var id:Long = 0,
    @SerializedName("name")
    val name:String?) : Serializable
