package com.example.trackt.data

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    //user
    @POST("api/Account/Create")
    suspend fun createUser(@Body user: Models.User): Response<Models.Response>

    @POST("api/Account/Login")
    suspend fun getUser(@Body user: Models.Login): Response<Models.LoginResponse>
    //suspend fun getUser(@Body user: Models.UserLogin): retrofit2.Response<Models.LoginResponse>

    //destination
    @POST("api/Destinations/Create")
    suspend fun createDestination(@Body destination: Models.Destination, @Header("Authorization") token: String): Response<Models.Response>
    //suspend fun createDestination(@Body destination: Models.Destination, @Header("Authorization") token: String)

    @GET("api/Destinations/Get")
    suspend fun getDestinations(@Header("Authorization") token: String): Models.TravelsResponse
    //fun getDestinations(@Header("Authorization") token: String): Flow<List<Models.DestinationResponse>>

    @GET("api/Destinations/Get?destinationId={num}")
    suspend fun getDestination(@Path("num") num: Int, @Header("Authorization") token: String): Models.TravelsResponse
    //suspend fun getDestination(@Path("num") num: Int, @Header("Authorization")token: String): Models.Destination

}

object RetrofitHelper {

    private const val baseUrl = "http://10.65.10.99:5053/"

    private fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            // we need to add converter factory to
            // convert JSON object to Java object
            .build()
    }

    val apiService: ApiService by  lazy { getInstance().create(ApiService::class.java) }
}