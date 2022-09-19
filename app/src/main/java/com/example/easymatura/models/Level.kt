package com.example.easymatura.models

import com.google.firebase.firestore.DocumentId
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Level(
    @DocumentId
    var id:Long = 0,
    @SerializedName("name")
    val name:String?,
    @SerializedName("questions")
    val questions:String?) : Serializable


