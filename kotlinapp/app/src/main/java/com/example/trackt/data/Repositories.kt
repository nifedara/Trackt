package com.example.trackt.data

import com.example.trackt.data.RetrofitHelper.apiService

class  UsersRepository {
    suspend fun createUser(user: Models.User): Models.Response {
        return apiService.createUser(user)}

    suspend fun getUser(user: Models.User) : Models.Response {
        return apiService.getUser(Models.Login(user.email, user.password))
    }
}
class  DestinationRepository {
    suspend fun createDestination(destination: Models.Destination, token: String): Models.Response {
        return apiService.createDestination(destination, "Bearer $token")}

    suspend fun getDestinations(token: String) : Models.Response {
        return apiService.getDestinations("Bearer $token")
    }
    suspend fun getDestination(num: Int, token: String) : Models.Response {
        return apiService.getDestination(num, "Bearer $token")
    }
}
