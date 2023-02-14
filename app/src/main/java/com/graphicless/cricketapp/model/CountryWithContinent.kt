package com.graphicless.cricketapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity
data class CountryWithContinent(
    @ColumnInfo(name = "continent_id") val continentId: Int,
    @ColumnInfo(name = "continent_name") val continentName: String,
    @ColumnInfo(name = "country_id") val countryId: Int,
    @ColumnInfo(name = "country_name") val countryName: String,
    @ColumnInfo(name = "country_flag") val countryFlag: String
)
