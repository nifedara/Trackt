package com.example.trackt.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    //user
    @POST("Account/Create/")
    suspend fun createUser(@Body user: Models.User)

    @GET("Account/Login")
    suspend fun getUser(@Field("email") email: String, @Field("password") password: String): String

    //destination
    @POST("Destinations/Create")
    suspend fun createDestination(@Body destination: Models.Destination, @Header("Authorization") token: String)

    @GET("Destinations/Get")
    suspend fun getDestinations(@Header("Authorization") token: String): List<Models.Destination>

    @GET("Destinations/Get?destinationId={num}")
    suspend fun getDestination(@Path("num") num: Int, @Header("Authorization")token: String): Models.Destination

}

object RetrofitHelper {

    private const val baseUrl = "http://10.65.10.97:5053/"

    private fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            // we need to add converter factory to
            // convert JSON object to Java object
            .build()
    }

    val apiService: ApiService by  lazy { getInstance().create(ApiService::class.java) }
}