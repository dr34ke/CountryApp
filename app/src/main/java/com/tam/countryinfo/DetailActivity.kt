package com.tam.countryinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.tam.countryinfo.Models.Country
import com.tam.countryinfo.ViewModels.DetailViewModel
import com.tam.countryinfo.ViewModels.MainViewModel
import com.tam.countryinfo.ui.theme.CountryInfoTheme
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

class DetailActivity: ComponentActivity() {
    private val viewModel: DetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val code = intent.getStringExtra("COUNTRY_CODE")

        if (code != null) {
            viewModel.getData(code)
        };
        setContent {
            CountryInfoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.LightGray
                ) {
                    CountryDetail(viewModel)
                }
            }
        }
    }
}
@Composable
fun CountryDetail(viewModel: DetailViewModel, modifier: Modifier=Modifier) {
    val uiState by viewModel.immutableCountryData.observeAsState(DetailViewModel.UiState())

    when {
        uiState.isLoading -> {
            Loader()
        }

        uiState.error != null -> {
            uiState.error?.let {Error(errorMessage = it)}
        }

        uiState.data != null -> {
            uiState.data?.let { DetailView(country = it[0]) }
        }
    }
}
@Composable
fun DetailView(country: Country) {
    Column (modifier=Modifier.padding(5.dp,5.dp)){
        AsyncImage(
            model = country.flags.values.first(),
            contentDescription = "flag",
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = country.name.official,
            fontSize = 30.sp
        )
        Text(
            text = "Population: "+country.population,
            fontSize = 20.sp
        )
        Text(
            text = "Capital: "+country.capital.first(),
            fontSize = 20.sp
        )
        Text(
            text = "Currencies: "+country?.currencies?.keys?.joinToString(
                separator = ", "
            ),
            fontSize = 20.sp
        )
        Text(
            text = "Region: "+country.region,
            fontSize = 20.sp
        )
    }

}