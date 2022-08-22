package com.example.coffeshop.domain.model

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

const val token =
    "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtZW5uYUBnbWFpbC5jb20iLCJpYXQiOjE2NjA3NDI5MDAsImV4cCI6MTY2MDgyOTMwMH0.T-1FDmyS1YG4wxgIxFkT3FS_0H0ohh3NvLU7CyiBd-heBzkSTu3QSBMoWr8jiBRJCKEdQR0TurPee-t7vnMdrg"

interface Api {
    @POST("/login/")
    fun login(@Body user: User): Call<LoginResponse>

    @POST("/register/")
    fun register(@Body user: FullUser): Call<Unit>

    @GET("/products?category=iced coffee")
    fun getAllIcedProducts(@Header("Authorization") accessToken: String): Call<MutableList<Coffee>>

    @GET("/products?category=hot coffee")
    fun getAllHotProducts(@Header("Authorization") accessToken: String): Call<MutableList<Coffee>>

    @DELETE("/products")
    fun delete(@Query("id") id:Long,@Header("Authorization") accessToken: String ):Call<Unit>

    @POST("/products/")
    fun addProduct(@Header("Authorization") accessToken: String ,@Body product: Product):Call<Unit>
}

val client = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    .build()

var retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://shopping-app-bm.herokuapp.com")
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()

var service: Api = retrofit.create(Api::class.java)