package com.example.trackt.data

import okhttp3.MultipartBody

class Models {

    data class User(
        val name: String,
        val email: String,
        val password: String,
    )

    data class Login(
        val email: String,
        val password: String
    )

//    data class Destination(
//        //@Part val image: MultipartBody.Part,
////        val destinationName: String,
////        val budget: Double,
////        val date: String,
//    )
data class Destination(
    val image: MultipartBody,
    val destinationName: MultipartBody,
    val budget: MultipartBody,
    val date: MultipartBody
)

    data class Response(
        val status: Boolean,
        val message: String,
        val data: String
    )

    data class TravelsResponse(
        val status: Boolean,
        val message: String,
        val data: List<DestinationResponse>
    ){
        data class DestinationResponse(
            val destinationId: Int,
            val destinationName: String,
            val imageUrl: String,
            val budget: Double,
            val date: String,
            val userId: String
        )
    }
    data class LoginResponse(
        val status: Boolean,
        val message: String,
        val data: OtherInfo
    ){
        data class OtherInfo(
            val token: String,
            val userInfo: UserInfo
        ){
            data class UserInfo(
                val name: String,
                val email: String
            )
        }
    }
}