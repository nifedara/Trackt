package com.example.trackt.data

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

    data class BaseResponse(
        val status: Boolean,
        val message: String,
        val data: String
    )

    data class DestinationResponse(
        val status: Boolean,
        val message: String,
        val data: List<Destination>
    ){
        data class Destination(
            val destinationId: Int,
            val destinationName: String,
            val imageUrl: String,
            val budget: Double,
            val date: String,
            val userId: String
        ) }

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
            ) } }
}