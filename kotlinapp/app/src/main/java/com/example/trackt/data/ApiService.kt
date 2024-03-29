package com.example.trackt.data

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    //user
    @POST("api/Account/Create")
    suspend fun createUser(@Body user: Models.User): Response<Models.BaseResponse>

    @POST("api/Account/Login")
    suspend fun getUser(@Body user: Models.Login): Response<Models.LoginResponse>

    //destination
    @Multipart
    @POST("api/Destinations/Create")
    suspend fun createDestination(@Part destinationName: MultipartBody.Part, @Part image: MultipartBody.Part,
                                  @Part budget: MultipartBody.Part, @Part date: MultipartBody.Part,
                                  @Header("Authorization") token: String): Response<Models.BaseResponse>
    //suspend fun createDestination(@Body destination: Models.Destination, @Header("Authorization") token: String): Response<Models.Response>

    @GET("api/Destinations/Get")
    suspend fun getDestinations(@Header("Authorization") token: String): Models.DestinationResponse
    //fun getDestinations(@Header("Authorization") token: String): Flow<List<Models.DestinationResponse>>

    @GET("api/Destinations/Get?destinationId={num}")
    suspend fun getDestination(@Path("num") num: Int, @Header("Authorization") token: String): Models.DestinationResponse
}


object RetrofitHelper {

    private const val baseUrl = "http://10.65.10.132/"

    private fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) //JSON object to Java object converter
            .build()
    }

    val apiService: ApiService by  lazy { getInstance().create(ApiService::class.java) }
}