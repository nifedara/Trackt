package com.example.trackt.data

import android.graphics.Bitmap
import com.google.type.DateTime

class Models {
    data class User(
        val name: String,
        val email: String,
        val password: String,
    )
    data class Destination(
        val image: Bitmap,
        val destination: String,
        val budget: Double,
        val date: DateTime,
    )
    data class DestinationResponse(
        val image: String,
        val destination: String,
        val budget: Double,
        val date: DateTime,
    )
}