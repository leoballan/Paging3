package com.vila.paging3test.data

import com.vila.paging3test.data.models.RandomUserResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserApi {
    @GET("/api")
    suspend fun getRamdomUsers(@Query("page")page:Int,
                               @Query("results")quantity:Int,
                               @Query("seed")seed:String = "abc"

    ): RandomUserResponseDto

}