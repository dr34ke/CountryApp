package com.tam.countryinfo.Service

import com.tam.countryinfo.Models.Country
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryService {
    @GET("/v3.1/all")
    suspend fun getCountryListResponse(): Response<List<Country>>
    companion object {
        private const val URL ="https://restcountries.com/"

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val countryService: CountryService by lazy{
            retrofit.create(CountryService::class.java)
        }
    }
    @GET("/v3.1/alpha/{code}")
    suspend fun getCountryResponse(@Path("code") code: String): Response<List<Country>>

}