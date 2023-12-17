package com.tam.countryinfo.Service

import com.tam.countryinfo.Models.Country
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CountryService {
    @GET("/v3.1/all")
    suspend fun getCountryResponse(): Response<List<Country>>
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
}