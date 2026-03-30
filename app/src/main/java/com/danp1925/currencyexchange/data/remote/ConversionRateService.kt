package com.danp1925.currencyexchange.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ConversionRateService {

    @GET("api/latest.json")
    suspend fun getLatest(@Query("app_id") appId : String) : GetLatestResponse

}