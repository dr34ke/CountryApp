package com.tam.countryinfo.ViewModels

import com.tam.countryinfo.Models.Country
import com.tam.countryinfo.Service.CountryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class MainViewModel :ViewModel() {
    private val countryRepository = CountryRepository();

    private val mutableCountriesData =  MutableLiveData<UiState<List<Country>>>()
    val immutableCountriesData: LiveData<UiState<List<Country>>> = mutableCountriesData

    data class UiState<T>(
        val data: T? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )

    fun getData(){
        viewModelScope.launch(Dispatchers.IO){
            try{
                mutableCountriesData.postValue(UiState(isLoading = true, error = null))
                val request = countryRepository.getCountries()
                val countries = request.body()
                mutableCountriesData.postValue(UiState(isLoading = false, data=countries, error = null))
            }
            catch(e:Exception){
                mutableCountriesData.postValue(UiState(isLoading = false, error = e.message));
            }
        }
    }
}