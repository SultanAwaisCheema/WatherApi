package com.example.watherapi.waterapi
import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("coord") val coord: Coord,
    @SerializedName("sys") val sys: Sys,
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("main") val main: Main,
    @SerializedName("wind") val wind: Wind,
    @SerializedName("rain") val rain: Rain,
    @SerializedName("clouds") val clouds: Clouds,
    @SerializedName("dt") val dt: Float,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("cod") val cod: Float
)

data class Weather(
    @SerializedName("id") val id: Int,
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)

data class Clouds(
    @SerializedName("all") val all: Float
)

data class Rain(
    @SerializedName("3h") val h3: Float
)

data class Wind(
    @SerializedName("speed") val speed: Float,
    @SerializedName("deg") val deg: Float
)

data class Main(
    @SerializedName("temp") val temp: Float,
    @SerializedName("humidity") val humidity: Float,
    @SerializedName("pressure") val pressure: Float,
    @SerializedName("temp_min") val temp_min: Float,
    @SerializedName("temp_max") val temp_max: Float
)

data class Sys(
    @SerializedName("country") val country: String,
    @SerializedName("sunrise") val sunrise: Long,
    @SerializedName("sunset") val sunset: Long
)

data class Coord(
    @SerializedName("lon") val lon: Float,
    @SerializedName("lat") val lat: Float
)
