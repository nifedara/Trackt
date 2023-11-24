package com.example.trackt.data

import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    //user
    @POST("Account/Create/")
    suspend fun createUser(@Body user: Models.User)

    @POST("Account/Login")
    suspend fun getUser(@Body user: Models.UserLogin): retrofit2.Response<Models.LoginResponse>

    //destination
    @POST("Destinations/Create")
    suspend fun createDestination(@Body destination: Models.Destination, @Header("Authorization") token: String)

    @GET("Destinations/Get")
    fun getDestinations(@Header("Authorization") token: String): Flow<List<Models.DestinationResponse>>

    @GET("Destinations/Get?destinationId={num}")
    suspend fun getDestination(@Path("num") num: Int, @Header("Authorization")token: String): Models.Destination

}

object RetrofitHelper {

    private const val baseUrl = "http:///192.168.160.94:5053/"

    private fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            // we need to add converter factory to
            // convert JSON object to Java object
            .build()
    }

    val apiService: ApiService by  lazy { getInstance().create(ApiService::class.java) }
}