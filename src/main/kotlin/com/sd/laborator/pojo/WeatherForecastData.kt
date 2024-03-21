package com.sd.laborator.pojo

data class WeatherForecastData(
    var latitude: Float,
    var longitude: Float,
    var date: String,
    var weatherCode: Int,
    var windDirection: Int,
    var windSpeed: Float,
    var temperature: Float,
    var apparentTemperature: Float,
    var rain: Int,
    var precipitation : Int
){}