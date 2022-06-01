package com.example.mvvmflow.service

import com.example.mvvmflow.model.UserModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("/api/users")
    suspend fun saveUser(
        @Field("name") name: String,
        @Field("job") job: String
    ) : UserModel

}