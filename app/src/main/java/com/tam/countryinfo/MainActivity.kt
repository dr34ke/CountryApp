package com.tam.countryinfo

import androidx.compose.runtime.getValue
import android.os.Bundle
import android.widget.ListView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tam.countryinfo.ViewModels.MainViewModel
import com.tam.countryinfo.ui.theme.CountryInfoTheme
import org.threeten.bp.ZoneId
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import com.tam.countryinfo.Models.Country

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getData()

        setContent {
            CountryInfoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.LightGray
                ) {
                    Country(viewModel)
                }
            }
        }
    }
}

@Composable
fun Country(viewModel: MainViewModel, modifier: Modifier=Modifier) {
    val uiState by viewModel.immutableCountriesData.observeAsState(MainViewModel.UiState())

    when {
        uiState.isLoading -> {
            Loader()
        }

        uiState.error != null -> {
            uiState.error?.let {Error(errorMessage = it)}
        }

        uiState.data != null -> {
            uiState.data?.let { ListView(countries = it) }
        }
    }
}


@Composable
fun Error(errorMessage:String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Snackbar(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            content = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(imageVector = Icons.Default.Warning, contentDescription = null)
                    Text(text = errorMessage, fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}

@Composable
fun Loader() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        LinearProgressIndicator(
            modifier = Modifier
                .width(80.dp)
                .height(80.dp)
        )
    }
}

@Composable
fun ListView(countries: List<Country>) {
    LazyColumn {
        if (countries.isNotEmpty()) {
            countries.forEachIndexed { index, country ->
                val _population = String.format("%,d", country.population).replace(",", " ")
                item {

                    Row {
                        Box(
                            modifier = Modifier
                                .width(100.dp)
                                .height(90.dp)
                                .padding(3.dp)
                        )
                        {
                            AsyncImage(
                                model = country.flags.values.first(),
                                contentDescription = "flag"
                            )
                        }
                        Column {
                            Text(
                                text = country.name.official,
                                fontSize = 25.sp
                            )
                            Row {
                                Text(
                                    text = "${country.name.common} - ${country.capital}",
                                    fontSize = 20.sp
                                )
                            }
                            Text(
                                text = "Population: $_population",
                                fontSize = 13.sp
                            )
                            Text(
                                text = "Currencies: ${
                                    country?.currencies?.keys?.joinToString(
                                        separator = ", "
                                    )
                                }",
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
    }
}