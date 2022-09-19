package com.example.easymatura.models

import com.google.firebase.firestore.DocumentId
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Quiz (
    @DocumentId
    var id:Long = 0,
    @SerializedName("question")
    val question:String?,
    @SerializedName("option_a")
    val option_a:String?,
    @SerializedName("option_b")
    val option_b:String?,
    @SerializedName("option_c")
    val option_c:String?,
    @SerializedName("option_d")
    val option_d:String?,
    @SerializedName("answer")
    val answer:String?) : Serializable