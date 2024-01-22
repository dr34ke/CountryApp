package com.tam.countryinfo.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tam.countryinfo.Models.Country
import com.tam.countryinfo.Service.CountryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {
    private val countryRepository = CountryRepository();

    private val mutableCountryData =  MutableLiveData<UiState<List<Country>>>()
    val immutableCountryData: LiveData<UiState<List<Country>>> = mutableCountryData

    data class UiState<T>(
        val data: T? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )

    fun getData(code: String){
        viewModelScope.launch(Dispatchers.IO){
            try{
                mutableCountryData.postValue(UiState(isLoading = true, error = null))
                val request = countryRepository.getCountry(code)
                val countries = request.body()
                mutableCountryData.postValue(UiState(isLoading = false, data=countries, error = null))
            }
            catch(e:Exception){
                mutableCountryData.postValue(UiState(isLoading = false, error = e.message));
            }
        }
    }
}