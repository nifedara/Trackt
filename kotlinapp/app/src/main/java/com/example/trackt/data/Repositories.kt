package com.example.trackt.data

import com.example.trackt.data.RetrofitHelper.apiService
import kotlinx.coroutines.flow.Flow

class  UsersRepository {
    suspend fun createUser(user: Models.User) {
        apiService.createUser(user)    }

    suspend fun getUser(user: Models.User) : String {
        return apiService.getUser(user.email, user.password)
    }
}
class  DestinationRepository {
    suspend fun createDestination(destination: Models.Destination, token: String) {
        apiService.createDestination(destination, "Bearer $token")    }

    suspend fun getDestinations(token: String) : List<Models.Destination> {
        return apiService.getDestinations("Bearer $token")
    }
    suspend fun getDestination(num: Int, token: String) : Models.Destination {
        return apiService.getDestination(num, "Bearer $token")
    }
}
