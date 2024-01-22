package com.tam.countryinfo.Models

data class Country(
    val name: Name,
    val capital : List<String>,
    val currencies: Map<String, Currency>,
    val flags:  Map<String, String>,
    val population: Int,
    val cca2: String,
    val region: String
)

data class Name(
    val common: String,
    val official: String
)
data class Currency(
    val name: String,
    val symbol: String
)