package com.example.trackt.data

import com.example.trackt.data.RetrofitHelper.apiService
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class  UsersRepository {
    suspend fun createUser(user: Models.User) {
        apiService.createUser(user)    }

    suspend fun getUser(user: Models.User) : Response<Models.LoginResponse> {
        return apiService.getUser(Models.UserLogin(user.email, user.password))
    }
}
class  DestinationRepository {
    suspend fun createDestination(destination: Models.Destination, token: String) {
        apiService.createDestination(destination, "Bearer $token")    }

    fun getDestinations(token: String?) : Flow<List<Models.DestinationResponse>> {
        return apiService.getDestinations("Bearer $token")
    }
    suspend fun getDestination(num: Int, token: String) : Models.Destination {
        return apiService.getDestination(num, "Bearer $token")
    }
}
