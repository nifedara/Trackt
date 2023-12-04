package com.example.trackt.data

import com.example.trackt.data.RetrofitHelper.apiService
import okhttp3.MultipartBody
import retrofit2.Response

class  UsersRepository {
    suspend fun createUser(user: Models.User): Response<Models.Response> {
        return apiService.createUser(user)}

    suspend fun getUser(user: Models.User) : Response<Models.LoginResponse> {
        return apiService.getUser(Models.Login(user.email, user.password))
    }
}
class  DestinationRepository {
    suspend fun createDestination(destination: MultipartBody, token: String): Response<Models.Response> {
        return apiService.createDestination(destination, "Bearer $token")}

    suspend fun getDestinations(token: String) : Models.TravelsResponse {
        return apiService.getDestinations("Bearer $token")
    }
    suspend fun getDestination(num: Int, token: String) : Models.TravelsResponse {
        return apiService.getDestination(num, "Bearer $token")
    }
}
