package com.tam.countryinfo.Service

import com.tam.countryinfo.Models.Country
import retrofit2.Response

class CountryRepository {
    suspend fun getCountries():Response<List<Country>> =
        CountryService.countryService.getCountryListResponse()
    suspend fun getCountry(code: String):Response<List<Country>> =
        CountryService.countryService.getCountryResponse(code);
}