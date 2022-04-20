package com.example.aplikasidummybinar

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("api/v1/auth/register")
    fun register(@Body register: RegisterRequest): Call<RegisterResponse>

    @POST("api/v1/auth/login")
    fun login(@Body login: LoginRequest): Call<LoginResponse>

    @GET("auth/me")
    fun auth(@Header ("Authorization") token: String): Call<RegisterResponse>

}